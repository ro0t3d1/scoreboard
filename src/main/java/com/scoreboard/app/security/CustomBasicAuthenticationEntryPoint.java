package com.scoreboard.app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoreboard.app.configuration.SecurityConfiguration;
import com.scoreboard.app.exception.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@ConditionalOnBean(CustomSecurityConfigurerAdapter.class)
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final String UNAUTHORIZED_ERROR_CODE = "unauthorized";

    private final ObjectMapper mapper;
    private final MessageSource messageSource;
    private final ApplicationContext applicationContext;

    @Autowired
    public CustomBasicAuthenticationEntryPoint(ObjectMapper mapper, MessageSource messageSource, ApplicationContext applicationContext) {
        this.mapper = mapper;
        this.messageSource = messageSource;
        this.applicationContext = applicationContext;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        response.addHeader("WWW-Authenticate", String.format("Basic realm=\"%s\"", getRealmName()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println(mapper.writeValueAsString(createUnAuthorizedErrorDto()));
    }

    private ErrorDto createUnAuthorizedErrorDto() {
        return ErrorDto.builder()
                .code(UNAUTHORIZED_ERROR_CODE)
                .message(messageSource.getMessage(UNAUTHORIZED_ERROR_CODE, null, LocaleContextHolder.getLocale()))
                .build();
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName(applicationContext.getId());
        super.afterPropertiesSet();
    }
}

