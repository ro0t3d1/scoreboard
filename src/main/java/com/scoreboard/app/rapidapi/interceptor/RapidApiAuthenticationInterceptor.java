package com.scoreboard.app.rapidapi.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RapidApiAuthenticationInterceptor implements ClientHttpRequestInterceptor {

    private static final String RAPID_API_KEY_HEADER_IDENTIFIER = "X-RapidAPI-Key";
    private static final String RAPID_API_HOST_HEADER_IDENTIFIER = "X-RapidAPI-Host";

    private final String rapidApiHost;
    private final String rapidApiKey;

    public RapidApiAuthenticationInterceptor(String rapidApiHost, String rapidApiKey) {
        this.rapidApiHost = rapidApiHost;
        this.rapidApiKey = rapidApiKey;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(RAPID_API_HOST_HEADER_IDENTIFIER, rapidApiHost);
        request.getHeaders().add(RAPID_API_KEY_HEADER_IDENTIFIER, rapidApiKey);
        return execution.execute(request, body);
    }
}
