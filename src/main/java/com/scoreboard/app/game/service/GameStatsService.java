package com.scoreboard.app.game.service;

import com.scoreboard.app.game.repository.GameStat;

public interface GameStatsService {

    GameStat getGameStats(Long gameId);

}
