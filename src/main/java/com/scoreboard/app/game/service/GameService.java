package com.scoreboard.app.game.service;

import com.scoreboard.app.game.repository.Game;

import java.util.List;

public interface GameService {

    Game getGame(Long id);

    List<Game> getGamesByDate(String date);

}
