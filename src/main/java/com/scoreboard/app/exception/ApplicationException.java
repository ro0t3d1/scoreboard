package com.scoreboard.app.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final String code;
    private final Object[] args;

    public ApplicationException(String code, Object... args) {
        super();
        this.code = code;
        this.args = args;
    }

}
