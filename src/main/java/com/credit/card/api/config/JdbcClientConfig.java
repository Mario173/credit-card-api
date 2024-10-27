package com.credit.card.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;

/**
 * Configuration class for JdbcClient
 */
@Configuration
public class JdbcClientConfig {

    /**
     * Method to create a JdbcClient bean
     *
     * @param dataSource data source
     * @return JdbcClient bean
     */
    @Bean
    public JdbcClient jdbcClient(DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}
