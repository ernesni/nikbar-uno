package com.ean.promo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {
	/*
	 * Esta clase de configuración es para que funcione la auditoria colocada en las tablas (Usuario)*/

    @Bean
    public AuditorAware<String> auditorProvider() {

        
        /*if you are using spring security, you can get the currently logged username with following code segment.
        SecurityContextHolder.getContext().getAuthentication().getName();*/

         
        return() -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
