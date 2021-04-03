package com.scoreboard.app.rapidapi.interceptor;

import com.scoreboard.app.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
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
        ClientHttpResponse response = execution.execute(request, body);
        if (response.getStatusCode().value() == HttpStatus.FORBIDDEN.value()) {
            log.warn("Detected invalid API key");
            throw new ApplicationException("rapidapi.invalidKey");
        }
        if (response.getStatusCode().value() == HttpStatus.TOO_MANY_REQUESTS.value()) {
            log.warn("RapidAPI rate limit exceeded.");
            throw new ApplicationException("rapidapi.rateLimit");
        }
        return response;
    }
}
