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
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class batchJob {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job batchJob() {
        return new JobBuilder("batchJob", jobRepository)
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

    // TODO: 구분자 기호 ','로 설정 관련 작업 필요함(데이터 안에 ,가 있는 경우 구분자로 인식될 수 있음)
    @Bean
    public ItemReader itemReader() {
        return new FlatFileItemReaderBuilder<OriginVOC>()
                .name("vocFile")
                .resource(new ClassPathResource("/filtered_data.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(OriginVOC.class)
                .linesToSkip(1) // 첫번째 행은 건너뜀(컬럼명은 건너뜀)
                .delimited().delimiter(",") // 구분자 기호 ,로 설정
                .names( "vocDvn", "receNo", "dealStat", "dealDtlStat", "cstNo", "cstNm", "cstRcgnNo",
                        "email", "hp", "telNo", "faxNo", "postNo", "addrBase", "addrDtl", "openYn",
                        "vocTit", "vocCntn", "vocNotes", "imprDire", "hopeEfct", "persCnt",
                        "infoPuseAgrYn", "infoPuseAgrDd", "receChnl", "receDvn", "receYmd",
                        "receUser", "receUserNm", "receDepCd", "receDepNm", "vocType", "vocFld",
                        "vocKd", "dealDday", "asgnVocYn", "getMn", "transMtr", "dealDepCd",
                        "dealDepNm", "dealDepSubCd", "dealDepSubNm", "dealYmd", "dealUser",
                        "answNotes", "answCntn", "docNo", "solvDvn", "rprtMeth", "dealType",
                        "relapYn", "sameVocYn", "dealDtCnt", "dealApvUser", "vocApvUser",
                        "cancelYn", "cancelResn", "cancelDttm", "epoDvn", "delYn", "regUser",
                        "regDd", "updtUser", "updtDd", "dealDepUser", "dealDepbNm", "dealDepcNm")
                .build();

    }

}
