package com.scoreboard.app.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class RapidApiConfiguration {

    private static final String RAPID_API_HOST = "free-nba.p.rapidapi.com";

    @Value("${rapidapi.key:#{null}}")
    private String rapidApiKey;

    public String getRapidApiHost() {
        return RAPID_API_HOST;
    }
}
