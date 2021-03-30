package com.scoreboard.app.rapidapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class RapidApiPagedResponseDto<T> {

    private List<T> data;
    private RapidApiPageDto meta;

}
