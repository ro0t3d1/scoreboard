package com.scoreboard.app.service;

import com.scoreboard.app.rapidapi.RapidApiHttpClient;
import com.scoreboard.app.rapidapi.dto.RapidApiGameDto;
import com.scoreboard.app.repository.Game;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Cacheable(cacheNames = "gameServiceCache")
public class GameServiceImplementation implements GameService {

    @Autowired
    private RapidApiHttpClient rapidApiHttpClient;

    @Override
    public Game getGame(Long gameId) {
        RapidApiGameDto game = rapidApiHttpClient.getGame(gameId);
       return createGameFromRapidApiGameDto(game);
    }

    @Override
    public List<Game> getGames(String date) {
        List<RapidApiGameDto> games = rapidApiHttpClient.getGames(date);
        return games.stream()
                .map(this::createGameFromRapidApiGameDto)
                .collect(Collectors.toList());
    }

    private Game createGameFromRapidApiGameDto(RapidApiGameDto rapidApiGameDto) {
        return Game.builder()
                .gameId(rapidApiGameDto.getId())
                .date(rapidApiGameDto.getDate())
                .homeTeamName(rapidApiGameDto.getHomeTeam().getName())
                .homeTeamScore(rapidApiGameDto.getHomeTeamScore())
                .visitorTeamName(rapidApiGameDto.getVisitorTeam().getName())
                .visitorTeamScore(rapidApiGameDto.getVisitorTeamScore())
                .build();
    }
}
