package org.job;

import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.job.jpa.FilteredVocRepository;
import org.job.reader.OriginVOC;
import org.job.writer.FilteredVOC;
import org.processor.ProcessedDataWrapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;

import static org.processor.VocProcessHelper.*;
import static org.valid.CaseValidation.*;
import static org.valid.CommonValidation.validateRequiredFields;


@Configuration
@RequiredArgsConstructor
public class BatchJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final FilteredVocRepository vocRepository;
    private final DataSource batchDataSource;
    private final DataSource resultDataSource;

    @Bean
    public Job migrationJob() throws Exception {
        return new JobBuilder("migrationJob", jobRepository)
                .start(validationStep())
                .next(migrationStep())
                .build();
    }

    @Bean
    public Step validationStep() {
        return new StepBuilder("validationStep", jobRepository)
                .<OriginVOC, FilteredVOC>chunk(100, transactionManager)
                .reader(originVocReader())
                .processor(validationProcessor())
                .writer(filiteredDataWriter())
                .faultTolerant()
                .skip(ValidationException.class)
                .listener(skipListener())
                .build();
    }

    @Bean
    public ItemProcessor<OriginVOC, FilteredVOC> validationProcessor() {
        return originVOC -> {
            validateRequiredFields(originVOC);
            validateCaseVocStatus(originVOC);

            org.job.writer.FilteredVOC filteredVOC = new org.job.writer.FilteredVOC();
            BeanUtils.copyProperties(originVOC, filteredVOC); // 동일한 필드 복사
            return filteredVOC;
        };
    }

    @Bean
    public ItemWriter<org.job.writer.FilteredVOC> filiteredDataWriter() {
        return items -> {
            vocRepository.saveAll(items);
        };
    }


    @Bean
    public SkipListener<FilteredVOC, org.job.writer.FilteredVOC> skipListener() {
        return new SkipListener<FilteredVOC, org.job.writer.FilteredVOC>() {
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
                .<org.job.writer.FilteredVOC, ProcessedDataWrapper>chunk(1000, transactionManager)
                .reader(migrationItemReader())
                .processor(migrationItemProcessor())
                .writer(new ItemWriter<ProcessedDataWrapper>() {
                    @Override
                    public void write(Chunk<? extends ProcessedDataWrapper> chunk) throws Exception {
                        System.out.println("chunk==========" + chunk);
                    }
                })
                .build();
    }

    private ItemProcessor<FilteredVOC, ? extends ProcessedDataWrapper> migrationItemProcessor() {
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
    public ItemReader<org.job.writer.FilteredVOC> migrationItemReader() throws Exception {

        return new JdbcPagingItemReaderBuilder<org.job.writer.FilteredVOC>()
                .name("migrationItemReader")
                .dataSource(batchDataSource)
                .pageSize(100)
                .rowMapper(new BeanPropertyRowMapper<>(org.job.writer.FilteredVOC.class))
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
