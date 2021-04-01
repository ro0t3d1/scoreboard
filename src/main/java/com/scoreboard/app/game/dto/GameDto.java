package com.scoreboard.app.game.dto;

import lombok.Data;

@Data
public class GameDto {

    private Long id;
    private String date;
    private TeamDto homeTeam;
    private TeamDto visitorTeam;

}
