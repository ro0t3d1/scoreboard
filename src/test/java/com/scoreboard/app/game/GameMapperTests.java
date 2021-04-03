package com.scoreboard.app.game;

import com.scoreboard.app.game.dto.GameDto;
import com.scoreboard.app.game.dto.GameMapper;
import com.scoreboard.app.game.dto.PlayerStatDto;
import com.scoreboard.app.game.repository.Game;
import com.scoreboard.app.game.repository.PlayerStat;
import com.scoreboard.app.utils.EntityCreator;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ExtendWith(MockitoExtension.class)
public class GameMapperTests {

    @Spy
    private GameMapper gameMapper = Mappers.getMapper(GameMapper.class);

    @Test
    public void shouldReturnGameDto() {
        Game game = EntityCreator.GameCreator.createGame();
        GameDto gameDto = gameMapper.gameToGameDto(game);
        assertThat(gameDto.getId()).isEqualTo(game.getId());
        assertThat(gameDto.getDate()).isEqualTo(game.getDate());
        assertThat(gameDto.getHomeTeam().getName()).isEqualTo(game.getHomeTeamName());
        assertThat(gameDto.getHomeTeam().getScore()).isEqualTo(game.getHomeTeamScore());
        assertThat(gameDto.getVisitorTeam().getName()).isEqualTo(game.getVisitorTeamName());
        assertThat(gameDto.getVisitorTeam().getScore()).isEqualTo(game.getVisitorTeamScore());
    }

    @Test
    public void shouldReturnGameStatDto() {
        PlayerStat playerStat = EntityCreator.GameStatsCreator.createPlayerStat();
        PlayerStatDto playerStatDto = gameMapper.playerStatToPlayerStatDto(playerStat);
        assertThat(playerStatDto.getPlayerName()).isEqualTo(playerStat.getPlayerName());
        assertThat(playerStatDto.getPlayerScore()).isEqualTo(playerStat.getPlayerScore());
    }

}
