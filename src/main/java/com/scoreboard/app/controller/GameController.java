package com.scoreboard.app.controller;

import com.scoreboard.app.dto.GameDto;
import com.scoreboard.app.dto.GameMapper;
import com.scoreboard.app.repository.Game;
import com.scoreboard.app.service.GameService;
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

    private final GameService gameService;
    private final GameMapper gameMapper;

    @Autowired
    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @GetMapping
    public ResponseEntity<List<GameDto>> getAllGames(@RequestParam(required = false) String date) {
        List<Game> games = gameService.getGames(date);
        return ResponseEntity.ok(gameMapper.gamesToGamesDto(games));
    }

    @GetMapping(path = "{gameId}")
    public ResponseEntity<GameDto> getAllGames(@PathVariable Long gameId) {
        Game game = gameService.getGame(gameId);
        return ResponseEntity.ok(gameMapper.gameToGameDto(game));
    }

}
