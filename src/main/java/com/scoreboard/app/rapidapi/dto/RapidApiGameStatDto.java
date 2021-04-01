package com.scoreboard.app.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RapidApiGameStatDto {

    @JsonProperty(value = "pts")
    private Integer points;
    private RapidApiPlayerDto player;

}
