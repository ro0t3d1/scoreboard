package com.scoreboard.app.configuration;

import com.scoreboard.app.rapidapi.interceptor.RapidApiAuthenticationInterceptor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;


@Data
@Configuration
public class RapidApiConfiguration {

    private static final String RAPID_API_HOST = "free-nba.p.rapidapi.com";

    @Value("${rapidapi.key:#{null}}")
    private String rapidApiKey;

    @Value("${rapidapi.http.connection.timeout: 5000}")
    private Integer connectionTimeout;

    @Value("${rapidapi.http.read.timeout: 10000}")
    private Integer readTimeout;

    public String getRapidApiHost() {
        return RAPID_API_HOST;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RapidApiAuthenticationInterceptor rapidApiAuthenticationInterceptor = new RapidApiAuthenticationInterceptor(RAPID_API_HOST, rapidApiKey);
        return builder
                .additionalInterceptors(rapidApiAuthenticationInterceptor)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

}
