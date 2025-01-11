package org.job.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Primary
    @Bean(name = "batchDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource batchDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "resultDataSource")
    @ConfigurationProperties(prefix = "datasource.from")
    public DataSource resultDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "jdbcTransactionManager")
    public PlatformTransactionManager jdbcTransactionManager(@Qualifier("resultDataSource") DataSource resultDataSource) {
        return new DataSourceTransactionManager(resultDataSource);
    }

    @Bean(name = "batchJdbcTemplate")
    public JdbcTemplate batchJdbcTemplate(@Qualifier("batchDataSource") DataSource batchDataSource) {
        return new JdbcTemplate(batchDataSource);
    }

    @Bean(name = "resultJdbcTemplate")
    public JdbcTemplate resultJdbcTemplate(@Qualifier("resultDataSource") DataSource resultDataSource) {
        return new JdbcTemplate(resultDataSource);
    }
}

