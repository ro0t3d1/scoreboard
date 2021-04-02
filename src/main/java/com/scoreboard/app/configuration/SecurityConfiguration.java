package com.scoreboard.app.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class SecurityConfiguration {

    @Value("${scoreboard.security.auth.enabled: scoreboard}")
    private Boolean authEnabled;

    @Value("${scoreboard.security.username: scoreboard}")
    private String username;

    @Value("${scoreboard.security.password: scoreboard}")
    private String password;

}
