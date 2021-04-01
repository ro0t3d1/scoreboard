package com.scoreboard.app.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDto {

    private String code;
    private String message;

}
