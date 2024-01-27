package com.poc.techvoice.userservice.application.config;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

    @Value("${audit.user}")
    private String userIdKey;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(MDC.get(userIdKey));
    }
}
