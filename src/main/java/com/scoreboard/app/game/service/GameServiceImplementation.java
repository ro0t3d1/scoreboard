package com.scoreboard.app.game.service;

import com.google.common.base.Strings;
import com.scoreboard.app.exception.ApplicationException;
import com.scoreboard.app.exception.ResourceNotFoundException;
import com.scoreboard.app.game.repository.Game;
import com.scoreboard.app.rapidapi.RapidApiHttpClient;
import com.scoreboard.app.rapidapi.dto.RapidApiGameDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameServiceImplementation implements GameService {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final RapidApiHttpClient rapidApiHttpClient;

    @Autowired
    public GameServiceImplementation(RapidApiHttpClient rapidApiHttpClient) {
        this.rapidApiHttpClient = rapidApiHttpClient;
    }

    @Override
    @Cacheable(cacheNames = "Games")
    public Game getGame(Long gameId) {
        Optional<RapidApiGameDto> rapidApiGameDto = rapidApiHttpClient.getGame(gameId);
        if (!rapidApiGameDto.isPresent()) {
            throw new ResourceNotFoundException("game", gameId);
        }
        return createGameFromRapidApiGameDto(rapidApiGameDto.get());
    }

    @Override
    @Cacheable(cacheNames = "Games")
    public List<Game> getGamesByDate(String date) {
        validateDate(date);
        return rapidApiHttpClient.getGamesByDate(date).stream()
                .map(this::createGameFromRapidApiGameDto)
                .collect(Collectors.toList());
    }

    private void validateDate(String date) {
        if (Strings.isNullOrEmpty(date)) {
            throw new ApplicationException("query.parameter.mandatory", "date");
        }
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            formatter.parse(date);
        } catch (ParseException e) {
            throw new ApplicationException("query.parameter.invalid.format", "date", DATE_FORMAT);
        }
    }

    private Game createGameFromRapidApiGameDto(RapidApiGameDto rapidApiGameDto) {
        return Game.builder()
                .id(rapidApiGameDto.getId())
                .date(rapidApiGameDto.getDate())
                .homeTeamName(rapidApiGameDto.getHomeTeam().getName())
                .homeTeamScore(rapidApiGameDto.getHomeTeamScore())
                .visitorTeamName(rapidApiGameDto.getVisitorTeam().getName())
                .visitorTeamScore(rapidApiGameDto.getVisitorTeamScore())
                .build();
    }

}
