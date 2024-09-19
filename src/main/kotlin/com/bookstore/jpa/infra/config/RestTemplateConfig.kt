package com.bookstore.jpa.infra.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Bean
    @Qualifier("defaultRestTemplate")
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}
