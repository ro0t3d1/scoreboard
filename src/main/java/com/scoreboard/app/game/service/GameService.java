package com.scoreboard.app.game.service;

import com.scoreboard.app.game.repository.Game;
import com.scoreboard.app.game.repository.GameStat;

import java.util.List;

public interface GameService {

    Game getGame(Long id);

    List<Game> getGamesByDate(String date);

    List<GameStat> getGameStats(Long gameId);

}
