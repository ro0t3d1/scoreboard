package com.scoreboard.app.game.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameStatDto {

    private GameDto game;
    private List<PlayerStatDto> playerStats;
}
