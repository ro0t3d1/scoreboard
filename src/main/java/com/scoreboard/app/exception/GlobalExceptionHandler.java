package com.scoreboard.app.exception;

import com.scoreboard.app.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String UNEXPECTED_ERROR = "unexpected";

    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDto> handleExceptions(ApplicationException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getCode(), ex.getArgs(), locale);
        ErrorDto errorDto = ErrorDto.builder()
                .code(ex.getCode())
                .message(errorMessage)
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleExceptions(ResourceNotFoundException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getCode(), ex.getArgs(), locale);
        ErrorDto errorDto = ErrorDto.builder()
                .code(ex.getCode())
                .message(errorMessage)
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleExceptions(Exception ex, Locale locale) {
        log.error("Unexpected exception occurred: ", ex);
        String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
        ErrorDto errorDto = ErrorDto.builder()
                .code(UNEXPECTED_ERROR)
                .message(errorMessage)
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}