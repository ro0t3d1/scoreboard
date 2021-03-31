package com.scoreboard.app.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ApplicationException {

    private static final String NOT_FOUND_ERROR_CODE = "resource.notFound";

    public ResourceNotFoundException(Object... args) {
        super(NOT_FOUND_ERROR_CODE, args);
    }

}
