package com.scoreboard.app.game.dto;

import com.scoreboard.app.game.repository.Game;
import com.scoreboard.app.game.repository.GameStat;
import com.scoreboard.app.game.repository.PlayerStat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GameMapper {

    List<GameDto> gamesToGamesDto(List<Game> game);

    @Mappings({
            @Mapping(source = "homeTeamName", target = "homeTeam.name"),
            @Mapping(source = "visitorTeamName", target = "visitorTeam.name"),
            @Mapping(source = "homeTeamScore", target = "homeTeam.score"),
            @Mapping(source = "visitorTeamScore", target = "visitorTeam.score")
    })
    GameDto gameToGameDto(Game game);

    GameStatDto gameStatToGameStatDto(GameStat gameStatDto);

    PlayerStatDto playerStatToPlayerStatDto(PlayerStat playerStatDto);

    default List<PlayerStatDto> playerStatsToPlayerStatsDto(List<PlayerStat> playerStats) {
        return playerStats.stream()
                .map(this::playerStatToPlayerStatDto)
                .collect(Collectors.toList());
    }

}
