package com.lookflow.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.lookflow.infrastructure.persistence.repository")
@EnableTransactionManagement
public class PersistenceConfig {
}

