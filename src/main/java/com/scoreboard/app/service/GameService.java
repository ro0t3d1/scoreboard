package com.scoreboard.app.service;

import com.scoreboard.app.repository.Game;

import java.util.List;

public interface GameService {

    Game getGame(Long id);

    List<Game> getGamesByDate(String date);

}
