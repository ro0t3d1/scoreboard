package com.scoreboard.app.configuration;

import com.scoreboard.app.rapidapi.interceptor.RapidApiAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    private final RapidApiConfiguration rapidApiConfiguration;

    @Value("${rapidapi.http.connection.timeout: 5000}")
    private Integer connectionTimeout;

    @Value("${rapidapi.http.read.timeout: 10000}")
    private Integer readTimeout;

    @Autowired
    public RestTemplateConfiguration(RapidApiConfiguration rapidApiConfiguration) {
        this.rapidApiConfiguration = rapidApiConfiguration;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RapidApiAuthenticationInterceptor rapidApiAuthenticationInterceptor = new RapidApiAuthenticationInterceptor(rapidApiConfiguration.getRapidApiHost(), rapidApiConfiguration.getRapidApiKey());
        return builder
                .additionalInterceptors(rapidApiAuthenticationInterceptor)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }
}
