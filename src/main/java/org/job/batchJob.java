package org.job;

import lombok.RequiredArgsConstructor;
import org.reader.OriginVOC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.beans.PropertyEditorSupport;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class batchJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job migrationJob() {
        return new JobBuilder("migrationJob", jobRepository)
                .start(step1())
                .build();
    }

    @Bean
    public Step step1(){
        return new StepBuilder("step1", jobRepository)
                .chunk(100, transactionManager)
                .reader(itemReader())
                .writer(new ItemWriter<Object>() {
                    @Override
                    public void write(Chunk<?> items) throws Exception {
                        System.out.println("items = " + items);
                    }
                })
                .build();
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
