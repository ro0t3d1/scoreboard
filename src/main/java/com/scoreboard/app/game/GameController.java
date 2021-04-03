package com.scoreboard.app.game;

import com.scoreboard.app.game.dto.GameDto;
import com.scoreboard.app.game.dto.GameMapper;
import com.scoreboard.app.game.dto.GameStatDto;
import com.scoreboard.app.game.repository.Game;
import com.scoreboard.app.game.repository.GameStat;
import com.scoreboard.app.game.service.GameService;
import com.scoreboard.app.game.service.GameStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("game")
public class GameController {

    private final GameStatsService gameStatsService;
    private final GameService gameService;
    private final GameMapper gameMapper;

    @Autowired
    public GameController(GameService gameService, GameMapper gameMapper, GameStatsService gameStatsService) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
        this.gameStatsService = gameStatsService;
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> getGames(@RequestParam(required = false) String date) {
        List<Game> games = gameService.getGamesByDate(date);
        return ResponseEntity.ok(gameMapper.gamesToGamesDto(games));
    }

    @GetMapping(path = "{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable Long gameId) {
        Game game = gameService.getGame(gameId);
        return ResponseEntity.ok(gameMapper.gameToGameDto(game));
    }

    @GetMapping(path = "{gameId}/stats")
    public ResponseEntity<GameStatDto> getGameStats(@PathVariable Long gameId) {
        GameStat gameStat = gameStatsService.getGameStats(gameId);
        return ResponseEntity.ok(gameMapper.gameStatToGameStatDto(gameStat));
    }

}
