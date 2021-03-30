package com.scoreboard.app.dto;

import lombok.Data;

@Data
public class GameDto {

    private Long gameId;
    // TODO: This probably must be a ZoneDateTime.
    private String date;
    private TeamDto homeTeam;
    private TeamDto visitorTeam;

}
