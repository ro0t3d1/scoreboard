package com.scoreboard.app.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RapidApiGameDto {

    private Long id;
    private String date;
    @JsonProperty(value = "home_team_score")
    private Integer homeTeamScore;
    @JsonProperty(value = "visitor_team_score")
    private Integer visitorTeamScore;
    @JsonProperty(value = "visitor_team")
    private RapidApiTeamDto visitorTeam;
    @JsonProperty(value = "home_team")
    private RapidApiTeamDto homeTeam;

}
