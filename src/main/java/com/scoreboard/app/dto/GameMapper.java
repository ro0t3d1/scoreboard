package com.scoreboard.app.dto;

import com.scoreboard.app.repository.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface GameMapper {

    List<GameDto> gamesToGamesDto(List<Game> game);

    @Mappings({
            @Mapping(source = "homeTeamName", target = "homeTeam.name"),
            @Mapping(source = "visitorTeamName", target = "visitorTeam.name"),
            @Mapping(source = "homeTeamScore", target = "homeTeam.score"),
            @Mapping(source = "visitorTeamScore", target = "visitorTeam.score")
    })
    GameDto gameToGameDto(Game game);
}
