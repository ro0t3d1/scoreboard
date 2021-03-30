package com.scoreboard.app.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    // TODO: When resource bundle is created this variable will be used.
    private final String code;

    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
    }

}
