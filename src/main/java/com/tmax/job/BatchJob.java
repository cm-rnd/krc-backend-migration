package com.tmax.job;

import com.tmax.job.jpa.FilteredVocRepository;
import lombok.RequiredArgsConstructor;
import com.tmax.job.reader.OriginVOC;
import com.tmax.job.writer.FilteredVOC;
import com.tmax.job.processor.ProcessedDataWrapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.tmax.job.processor.VocProcessHelper.*;
import static com.tmax.valid.CaseValidation.*;
import static com.tmax.valid.CommonValidation.validateRequiredFields;


@Configuration
@RequiredArgsConstructor
public class BatchJob {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final FilteredVocRepository vocRepository;

    private final JdbcTemplate resultJdbcTemplate;

    private final DataSource batchDataSource;

    @Bean
    public Job migrationJob41() throws Exception {
        return new JobBuilder("migrationJob41", jobRepository)
                .start(migrationStep())
//                .start(validationStep())
//                .next(migrationStep())
                .build();
    }

    @Bean
    public Step validationStep() {
        return new StepBuilder("validationStep", jobRepository)
                .<OriginVOC, FilteredVOC>chunk(100, transactionManager)
                .reader(originVocReader())
                .processor(validationProcessor())
                .writer(filiteredDataWriter())
//                .faultTolerant()
//                .skip(ValidationException.class)
//                .listener(skipListener())
                .build();
    }

    @Bean
    public ItemProcessor<OriginVOC, FilteredVOC> validationProcessor() {
        return originVOC -> {
            validateRequiredFields(originVOC);
            validateCaseVocStatus(originVOC);

            FilteredVOC filteredVOC = new FilteredVOC();
            BeanUtils.copyProperties(originVOC, filteredVOC); // 동일한 필드 복사
            return filteredVOC;
        };
    }

    @Bean
    @Transactional(transactionManager = "transactionManager")
    public ItemWriter<FilteredVOC> filiteredDataWriter() {
        return items -> {
            vocRepository.saveAll(items);
        };
    }


    @Bean
    public SkipListener<FilteredVOC, FilteredVOC> skipListener() {
        return new SkipListener<FilteredVOC, FilteredVOC>() {
            @Override
            public void onSkipInProcess(FilteredVOC item, Throwable t) {
                System.out.println("Skipped item: " + item);
                logSkippedItem(item, t);
            }

            private void logSkippedItem(FilteredVOC item, Throwable t) {
                try (FileWriter writer = new FileWriter("skipped_items.csv", true)) {
                    writer.write(item.toString() + ", Reason: " + t.getMessage() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    @Bean
    public Step migrationStep() throws Exception {
        return new StepBuilder("migrationStep", jobRepository)
                .<FilteredVOC, ProcessedDataWrapper>chunk(1000, transactionManager)
                .reader(migrationItemReader())
                .processor(migrationItemProcessor())
                .writer(migrationItemWriter())
                .build();
    }

    @Bean
    public ItemProcessor<FilteredVOC, ? extends ProcessedDataWrapper> migrationItemProcessor() {
        return filteredVoc -> {
            ProcessedDataWrapper dataWrapper = new ProcessedDataWrapper();
            switch (filteredVoc.getDealStat()) {
                case "2", "3" -> {
                    processVoc(filteredVoc, dataWrapper);
                    processKrccVoc(filteredVoc, dataWrapper);
                    processVocProcess(filteredVoc, dataWrapper);
                    processVocResultNotiTypeRelation(filteredVoc, dataWrapper);
                    processVocProgressNotiTypeRelation(filteredVoc, dataWrapper);
                }
                case "4", "5", "9" -> {
                    processVoc(filteredVoc, dataWrapper);
                    processKrccVoc(filteredVoc, dataWrapper);
                    processVocProcess(filteredVoc, dataWrapper);
                    processVocResultNotiTypeRelation(filteredVoc, dataWrapper);
                    processVocProgressNotiTypeRelation(filteredVoc, dataWrapper);
                    processAnswer(filteredVoc, dataWrapper);
                }
            }
            return dataWrapper;
        };
    }

    @Bean
    public ItemReader<FilteredVOC> migrationItemReader() throws Exception {

        return new JdbcPagingItemReaderBuilder<FilteredVOC>()
                .name("migrationItemReader")
                .dataSource(batchDataSource)
                .pageSize(100)
                .rowMapper(new BeanPropertyRowMapper<>(FilteredVOC.class))
                .queryProvider(queryProvider())
                .build();
    }

    @Bean
    public PagingQueryProvider queryProvider() throws Exception {

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(batchDataSource);
        queryProvider.setSelectClause("*"); // 필요한 컬럼
        queryProvider.setFromClause("from filteredvoc");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);
        return queryProvider.getObject();
    }

    @Bean
    public ItemWriter<ProcessedDataWrapper> migrationItemWriter() {
        return items -> {
            for (ProcessedDataWrapper dataWrapper : items) {
                AtomicReference<Long> vocIdRef = new AtomicReference<>();
                AtomicReference<Long> vocProcessIdRef = new AtomicReference<>();
                AtomicReference<Long> vocKrccAntiCorruptionIdRef = new AtomicReference<>();
                AtomicReference<Long> vocKrccGeneralIdRef = new AtomicReference<>();


                if (dataWrapper.getVoc() != null) {
                    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

                    resultJdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO VOC (VOC_CLASSIFICATION, VOC_MANUAL_GENERAL_ID, VOC_MANUAL_ANTI_CORRUPTION_ID, VOC_KRCC_GENERAL_ID, " +
                                        "VOC_KRCC_ANTI_CORRUPTION_ID, VOC_NUMBER, REPORTER_NAME, REPORTED_AT, PHONE, MOBILE, FAX, EMAIL, " +
                                        "ADDRESS_ID, RECEIPT_TYPE, TITLE, CONTENTS, REGISTER_ID, VOC_PROCESS_ID, DELETED, " +
                                        "REGISTERED_AT, CREATED_AT, UPDATED_AT, NUMBER_OF_REPORTERS, HITS, RECEIPT_CHANNEL_ID) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                new String[] { "ID" }
                        );
                        ps.setString(1, dataWrapper.getVoc().getVocClassification());
                        ps.setObject(2, dataWrapper.getVoc().getVocManualGeneralId());
                        ps.setObject(3, dataWrapper.getVoc().getVocManualAntiCorruptionId());
                        ps.setObject(4, dataWrapper.getVoc().getVocKrccGeneralId());
                        ps.setObject(5, dataWrapper.getVoc().getVocKrccAntiCorruptionId());
                        ps.setString(6, dataWrapper.getVoc().getVocNumber());
                        ps.setString(7, dataWrapper.getVoc().getReporterName());
                        ps.setObject(8, dataWrapper.getVoc().getReportedAt());
                        ps.setString(9, dataWrapper.getVoc().getPhone());
                        ps.setString(10, dataWrapper.getVoc().getMobile());
                        ps.setString(11, dataWrapper.getVoc().getFax());
                        ps.setString(12, dataWrapper.getVoc().getEmail());
                        ps.setObject(13, dataWrapper.getVoc().getAddressId());
                        ps.setString(14, dataWrapper.getVoc().getReceiptType());
                        ps.setString(15, dataWrapper.getVoc().getTitle());
                        ps.setString(16, dataWrapper.getVoc().getContents());
                        ps.setObject(17, dataWrapper.getVoc().getRegisterId());
                        ps.setObject(18, dataWrapper.getVoc().getVocProcessId());
                        ps.setBoolean(19, dataWrapper.getVoc().getDelete());
                        ps.setObject(20, dataWrapper.getVoc().getRegisteredAt());
                        ps.setObject(21, dataWrapper.getVoc().getCreatedAt());
                        ps.setObject(22, dataWrapper.getVoc().getUpdatedAt());
                        ps.setObject(23, dataWrapper.getVoc().getNumberOfReporters());
                        ps.setObject(24, dataWrapper.getVoc().getHits());
                        ps.setObject(25, dataWrapper.getVoc().getReceiptChannelId());
                        return ps;
                    }, keyHolder);

                    vocIdRef.set(Objects.requireNonNull(keyHolder.getKey()).longValue());
                }

                if (dataWrapper.getAnswer() != null) {
                    resultJdbcTemplate.update(
                            "INSERT INTO ANSWER (VOC_ID, WRITER_ID, TITLE, IS_PUBLIC_WITHIN_COMPANY, COMPLAINT_CLASSIFICATION_ID, COMPLAINT_TYPE, " +
                                    "COMPLAINT_CATEGORY, PROCESSING_TYPE, RESOLUTION_STATUS, NOTIFICATION_METHOD, IS_SAME_COMPLAINT, " +
                                    "IS_REOCCURRING, CONTENT, CREATED_AT, UPDATED_AT, HITS) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                            vocIdRef.get(),
                            dataWrapper.getAnswer().getWriterId(),
                            dataWrapper.getAnswer().getTitle(),
                            dataWrapper.getAnswer().isPublicWithinCompany(),
                            dataWrapper.getAnswer().getComplaintClassificationId(),
                            dataWrapper.getAnswer().getComplaintType(),
                            dataWrapper.getAnswer().getComplaintCategory(),
                            dataWrapper.getAnswer().getProcessingType(),
                            dataWrapper.getAnswer().getResolutionStatus(),
                            dataWrapper.getAnswer().getNotificationMethod(),
                            dataWrapper.getAnswer().getIsSameComplaint(),
                            dataWrapper.getAnswer().getIsReoccurring(),
                            dataWrapper.getAnswer().getContent(),
                            dataWrapper.getAnswer().getCreatedAt(),
                            dataWrapper.getAnswer().getUpdatedAt(),
                            dataWrapper.getAnswer().getHits()
                    );

                }

                if (dataWrapper.getVocKrccAntiCorruption() != null) {
                    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

                    resultJdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO VOC_KRCC_ANTI_CORRUPTION (KRCC_REPORTER_ID, REPRESENTATIVE, VISIBILITY, EXPECTED_EFFECT, " +
                                        "IMPROVEMENT_PLAN, AGREED_INVESTIGATION_BY_RELATED_AGENCY, AGREED_INVESTIGATION_BY_OTHER_AGENCY, " +
                                        "AGREED_TRANSFER_INVESTIGATION, REPORTEE_NAME, REPORTEE_ORGANIZATION, REPORTEE_DEPARTMENT, " +
                                        "REPORTEE_ADDRESS_ID, REPORTEE_CONTACT) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                new String[]{"ID"}
                        );
                        ps.setObject(1, dataWrapper.getVocKrccAntiCorruption().getKrccReporterId());
                        ps.setBoolean(2, dataWrapper.getVocKrccAntiCorruption().getRepresentative());
                        ps.setBoolean(3, dataWrapper.getVocKrccAntiCorruption().getVisibility());
                        ps.setString(4, dataWrapper.getVocKrccAntiCorruption().getExpectedEffect());
                        ps.setString(5, dataWrapper.getVocKrccAntiCorruption().getImprovementPlan());
                        ps.setBoolean(6, dataWrapper.getVocKrccAntiCorruption().getAgreedInvestigationByRelatedAgency());
                        ps.setBoolean(7, dataWrapper.getVocKrccAntiCorruption().getAgreedInvestigationByOtherAgency());
                        ps.setBoolean(8, dataWrapper.getVocKrccAntiCorruption().getAgreedTransferInvestigation());
                        ps.setString(9, dataWrapper.getVocKrccAntiCorruption().getReporteeName());
                        ps.setString(10, dataWrapper.getVocKrccAntiCorruption().getReporteeOrganization());
                        ps.setString(11, dataWrapper.getVocKrccAntiCorruption().getReporteeDepartment());
                        ps.setObject(12, dataWrapper.getVocKrccAntiCorruption().getReporteeAddressId());
                        ps.setString(13, dataWrapper.getVocKrccAntiCorruption().getReporteeContact());
                        return ps;
                    }, keyHolder);

                    vocKrccAntiCorruptionIdRef.set(Objects.requireNonNull(keyHolder.getKey()).longValue());
                }


                if (dataWrapper.getVocKrccGeneral() != null) {
                    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

                    resultJdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO VOC_KRCC_GENERAL (KRCC_REPORTER_ID, VISIBILITY, EXPECTED_EFFECT, IMPROVEMENT_PLAN) " +
                                        "VALUES (?, ?, ?, ?)",
                                new String[]{"ID"}
                        );
                        ps.setObject(1, dataWrapper.getVocKrccGeneral().getKrccReporterId());
                        ps.setBoolean(2, dataWrapper.getVocKrccGeneral().getVisibility());
                        ps.setString(3, dataWrapper.getVocKrccGeneral().getExpectedEffect());
                        ps.setString(4, dataWrapper.getVocKrccGeneral().getImprovementPlan());
                        return ps;
                    }, keyHolder);

                    vocKrccGeneralIdRef.set(Objects.requireNonNull(keyHolder.getKey()).longValue());
                }


                if (dataWrapper.getVocProcess() != null) {
                    GeneratedKeyHolder vocProcessKeyHolder = new GeneratedKeyHolder();
                    resultJdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO VOC_PROCESS (VOC_ID, STATUS_CODE, VOC_TYPE_ID, VOC_TYPE_ORIGIN_NAME, MAIN_RECEPTIONIST_ID, " +
                                        "SUB_RECEPTIONIST_ID, DUE_DATE, DUE_DATE_CHANGE_REASON, ORGANIZATION_ASSIGNER_ID, ORGANIZATION_ID, " +
                                        "MAIN_ASSIGNER_ID, SUB_ASSIGNER_ID, MANAGER_ID, CREATED_AT, UPDATED_AT) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                                new String[] { "ID" }
                        );
                        ps.setObject(1, vocIdRef.get());
                        ps.setString(2, dataWrapper.getVocProcess().getStatusCode());
                        ps.setObject(3, dataWrapper.getVocProcess().getVocTypeId());
                        ps.setString(4, dataWrapper.getVocProcess().getVocTypeOriginName());
                        ps.setObject(5, dataWrapper.getVocProcess().getMainReceptionistId());
                        ps.setObject(6, dataWrapper.getVocProcess().getSubReceptionistId());
                        ps.setObject(7, dataWrapper.getVocProcess().getDueDate());
                        ps.setString(8, dataWrapper.getVocProcess().getDueDateChangeReason());
                        ps.setObject(9, dataWrapper.getVocProcess().getOrganizationAssignerId());
                        ps.setObject(10, dataWrapper.getVocProcess().getOrganizationId());
                        ps.setObject(11, dataWrapper.getVocProcess().getMainAssignerId());
                        ps.setObject(12, dataWrapper.getVocProcess().getSubAssignerId());
                        ps.setObject(13, dataWrapper.getVocProcess().getManagerId());
                        ps.setObject(14, dataWrapper.getVocProcess().getCreatedAt());
                        ps.setObject(15, dataWrapper.getVocProcess().getUpdatedAt());
                        return ps;
                    }, vocProcessKeyHolder);

                    vocProcessIdRef.set(Objects.requireNonNull(vocProcessKeyHolder.getKey()).longValue());
                }

                if (vocIdRef.get() != null && vocProcessIdRef.get() != null) {
                    if(vocKrccAntiCorruptionIdRef.get() != null) {
                        resultJdbcTemplate.update(
                                "UPDATE VOC SET VOC_PROCESS_ID = ?, VOC_KRCC_ANTI_CORRUPTION_ID = ?  WHERE ID = ?",
                                vocProcessIdRef.get(),
                                vocKrccAntiCorruptionIdRef.get(),
                                vocIdRef.get()
                        );
                    } else if(vocKrccGeneralIdRef.get() != null) {
                        resultJdbcTemplate.update(
                                "UPDATE VOC SET VOC_PROCESS_ID = ?, VOC_KRCC_GENERAL_ID = ? WHERE ID = ?",
                                vocProcessIdRef.get(),
                                vocKrccGeneralIdRef.get(),
                                vocIdRef.get()
                        );
                    }
                }

                if (dataWrapper.getVocProgressNotiTypeRelation() != null) {
                    resultJdbcTemplate.update(
                            "INSERT INTO VOC_PROGRESS_NOTI_TYPE_RELATION (VOC_ID, NOTIFICATION_TYPE) " +
                                    "VALUES (?, ?)",
                            vocIdRef.get(),
                            dataWrapper.getVocProgressNotiTypeRelation().getNotificationType()
                    );
                }

                if (dataWrapper.getVocResultNotiTypeRelation() != null) {
                    resultJdbcTemplate.update(
                            "INSERT INTO VOC_RESULT_NOTI_TYPE_RELATION (VOC_ID, NOTIFICATION_TYPE) " +
                                    "VALUES (?, ?)",
                            vocIdRef.get(),
                            dataWrapper.getVocResultNotiTypeRelation().getNotificationType()
                    );
                }


            }
        };
    }



    @Bean
    public FlatFileItemReader<OriginVOC> originVocReader() {
        FlatFileItemReader<OriginVOC> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/filtered_data.csv"));
        reader.setLinesToSkip(1);
        reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());

        DefaultLineMapper<OriginVOC> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(createTokenizer());
        lineMapper.setFieldSetMapper(createFieldSetMapper());

        reader.setLineMapper(lineMapper);
        return reader;
    }

    private DelimitedLineTokenizer createTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setQuoteCharacter('"');
        tokenizer.setNames(
                "vocDvn", "receNo", "dealStat", "dealDtlStat", "cstNo", "cstNm", "cstRcgnNo", "email", "hp",
                "telNo", "faxNo", "postNo", "addrBase", "addrDtl", "openYn", "vocTit", "vocCntn", "vocNotes",
                "imprDire", "hopeEfct", "persCnt", "infoPuseAgrYn", "infoPuseAgrDd", "receChnl", "receDvn",
                "receYmd", "receUser", "receUserNm", "receDepCd", "receDepNm", "vocType", "vocFld", "vocKd",
                "dealDday", "asgnVocYn", "getMn", "transMtr", "dealDepCd", "dealDepNm", "dealDepSubCd",
                "dealDepSubNm", "dealYmd", "dealUser", "answNotes", "answCntn", "docNo", "solvDvn", "rprtMeth",
                "dealType", "relapYn", "sameVocYn", "dealDtCnt", "dealApvUser", "vocApvUser", "cancelYn",
                "cancelResn", "cancelDttm", "epoDvn", "delYn", "regUser", "regDd", "updtUser", "updtDd",
                "dealDepUser", "dealDepbNm", "dealDepcNm"
        );
        return tokenizer;
    }

    private BeanWrapperFieldSetMapper<OriginVOC> createFieldSetMapper() {
        BeanWrapperFieldSetMapper<OriginVOC> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(OriginVOC.class);
        return fieldSetMapper;
    }

}
