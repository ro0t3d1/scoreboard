package com.scoreboard.app.game.service;

import com.scoreboard.app.game.repository.GameStat;
import com.scoreboard.app.game.repository.PlayerStat;
import com.scoreboard.app.rapidapi.RapidApiHttpClient;
import com.scoreboard.app.rapidapi.dto.RapidApiGameStatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GameStatsServiceImplementation implements GameStatsService {


    private final RapidApiHttpClient rapidApiHttpClient;
    private final GameService gameService;

    @Autowired
    public GameStatsServiceImplementation(GameService gameService, RapidApiHttpClient rapidApiHttpClient) {
        this.gameService = gameService;
        this.rapidApiHttpClient = rapidApiHttpClient;
    }

    @Override
    @Cacheable(cacheNames = "GameStats")
    public GameStat getGameStats(Long gameId) {
        GameStat gameStat = new GameStat();
        gameStat.setGame(gameService.getGame(gameId));
        gameStat.setPlayerStats(getPlayerStats(gameId));
        return gameStat;
    }

    private List<PlayerStat> getPlayerStats(Long gameId) {
        List<PlayerStat> playerStats = new ArrayList<>();
        for (RapidApiGameStatDto rapidApiGameStatDto : rapidApiHttpClient.getGameStats(gameId)) {
            if (rapidApiGameStatDto.getPoints() != null && rapidApiGameStatDto.getPoints() != 0 &&
                    rapidApiGameStatDto.getPlayer() != null && rapidApiGameStatDto.getPlayer().getId() != null) {
                playerStats.add(createGameStatFromRapidApiGameStatDto(rapidApiGameStatDto));
            }
        }
        return playerStats;
    }

    private PlayerStat createGameStatFromRapidApiGameStatDto(RapidApiGameStatDto rapidApiGameStatDto) {
        return PlayerStat.builder()
                .playerName(String.format("%s %s", rapidApiGameStatDto.getPlayer().getFirstName(), rapidApiGameStatDto.getPlayer().getLastName()))
                .playerScore(rapidApiGameStatDto.getPoints())
                .build();
    }
}
