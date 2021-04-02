package com.scoreboard.app.security;

import com.scoreboard.app.configuration.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "scoreboard.auth.enabled", havingValue = "true", matchIfMissing = true)
@Import({ManagementWebSecurityAutoConfiguration.class, SecurityAutoConfiguration.class})
public class CustomSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final CustomBasicAuthenticationEntryPoint authenticationEntryPoint;
    private final SecurityConfiguration securityConfiguration;

    @Autowired
    public CustomSecurityConfigurerAdapter(CustomBasicAuthenticationEntryPoint authenticationEntryPoint, SecurityConfiguration securityConfiguration) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.securityConfiguration = securityConfiguration;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(securityConfiguration.getUsername())
                .password(passwordEncoder().encode(securityConfiguration.getPassword()))
                .authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .requestMatchers(EndpointRequest.to(HealthEndpoint.class, PrometheusScrapeEndpoint.class))
                .permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
