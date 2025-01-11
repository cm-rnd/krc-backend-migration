package org.job;

import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.processor.ProcessedDataWrapper;
import org.reader.OriginVOC;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.valid.CaseValidation.*;
import static org.valid.CommonValidation.validateRequiredFields;


@Configuration
@RequiredArgsConstructor
public class batchJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
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
                .<OriginVOC, OriginVOC>chunk(100, transactionManager)
                .reader(itemReader()) // 데이터를 읽는 Reader
                .processor(validationProcessor()) // 검증 로직을 처리하는 Processor
                .writer(validationWriter()) // 검증된 데이터를 처리하는 Writer
                .faultTolerant() // 검증 실패 처리
                .skip(ValidationException.class) // Validation 실패 데이터 무시
                .skipLimit(1000)
                .build();
    }

    @Bean
    public ItemProcessor<OriginVOC, OriginVOC> validationProcessor() {
        return originVOC -> {
            validateRequiredFields(originVOC);
            validateCaseVocStatus(originVOC);
            return originVOC;
        };
    }

    @Bean
    public ItemWriter<OriginVOC> validationWriter() {
        return items -> {
            // 검증된 데이터 저장 또는 로깅 (파일, DB 등)
            items.forEach(item -> System.out.println("Validated Data: " + item));
        };
    }

    @Bean
    public SkipListener<OriginVOC, ProcessedDataWrapper> validationSkipListener() {
        return new SkipListener<>() {
            @Override
            public void onSkipInProcess(OriginVOC item, Throwable t) {
                System.err.println("검증 실패: " + item + ", 이유: " + t.getMessage());
            }
        };
    }


    @Bean
    public Step migrationStep() throws Exception {
        return new StepBuilder("migrationStep", jobRepository)
                .<OriginVOC, ProcessedDataWrapper>chunk(1000, transactionManager)
                .reader(migrationItemReader())
                .writer(new ItemWriter<ProcessedDataWrapper>() {
                    @Override
                    public void write(Chunk<? extends ProcessedDataWrapper> chunk) throws Exception {

                    }
                })
                .build();
    }

    @Bean
    public ItemReader<OriginVOC> migrationItemReader() throws Exception {

        return new JdbcPagingItemReaderBuilder<OriginVOC>()
                .name("migrationItemReader")
                .dataSource(batchDataSource)
                .pageSize(100)
                .rowMapper(new BeanPropertyRowMapper<>(OriginVOC.class))
                .queryProvider(queryProvider())
                .build();
    }

    @Bean
    public PagingQueryProvider queryProvider() throws Exception {

        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(batchDataSource);
        queryProvider.setSelectClause("vocDvn, receNo, dealStat, dealDtlStat, cstNo, cstNm, cstRcgnNo, email, hp, telNo, faxNo, postNo, addrBase, addrDtl, openYn, vocTit, vocCntn, vocNotes, imprDire, hopeEfct, persCnt, infoPuseAgrYn, infoPuseAgrDd, receChnl, receDvn, receYmd, receUser, receUserNm, receDepCd, receDepNm, vocType, vocFld, vocKd, dealDday, asgnVocYn, getMn, transMtr, dealDepCd, dealDepNm, dealDepSubCd, dealDepSubNm, dealYmd, dealUser, answNotes, answCntn, docNo, solvDvn, rprtMeth, dealType, relapYn, sameVocYn, dealDtCnt, dealApvUser, vocApvUser, cancelYn, cancelResn, cancelDttm, epoDvn, delYn, regUser, regDd, updtUser, updtDd, dealDepUser, dealDepbNm, dealDepcNm"); // 필요한 컬럼
        queryProvider.setFromClause("from origin_voc");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);
        return queryProvider.getObject();
    }


    @Bean
    public FlatFileItemReader<OriginVOC> itemReader() {
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
