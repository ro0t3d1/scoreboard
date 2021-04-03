package com.scoreboard.app.utils;

import com.scoreboard.app.comment.dto.CommentDto;
import com.scoreboard.app.comment.repository.Comment;
import com.scoreboard.app.game.repository.Game;
import com.scoreboard.app.game.repository.PlayerStat;

import java.time.ZonedDateTime;
import java.util.UUID;

public class EntityCreator {

    public static class CommentCreator {

        private static final String COMMENT_ID = UUID.randomUUID().toString();
        private static final String COMMENT_TEXT = "Testing comment";
        private static final Long GAME_ID = 1L;
        private static final ZonedDateTime MODIFIED_DATE = ZonedDateTime.now();

        public static Comment createComment() {
            return Comment.builder()
                    .gameId(GAME_ID)
                    .text(COMMENT_TEXT)
                    .id(COMMENT_ID)
                    .modifiedDate(MODIFIED_DATE)
                    .build();
        }

        public static CommentDto createCommentDto() {
            CommentDto commentDto = new CommentDto();
            commentDto.setId(COMMENT_ID);
            commentDto.setText(COMMENT_TEXT);
            commentDto.setGameId(GAME_ID);
            commentDto.setModifiedDate(MODIFIED_DATE);
            return commentDto;
        }
    }

    public static class GameCreator {

        private static final Long GAME_ID = 1L;
        private static final String VISITOR_TEAM_NAME = "VISITOR";
        private static final Integer VISITOR_TEAM_SCORE = 10;
        private static final String HOME_TEAM_NAME = "HOME";
        private static final Integer HOME_TEAM_SCORE = 10;
        private static final String DATE = "2019-02-09 00:00:00 UTC";

        public static Game createGame() {
            return Game.builder()
                    .id(GAME_ID)
                    .homeTeamName(HOME_TEAM_NAME)
                    .homeTeamScore(HOME_TEAM_SCORE)
                    .visitorTeamName(VISITOR_TEAM_NAME)
                    .visitorTeamScore(VISITOR_TEAM_SCORE)
                    .date(DATE)
                    .build();
        }
    }

    public static class GameStatsCreator {

        private static final String PLAYER_NAME = "PLAYER";
        private static final Integer PLAYER_SCORE = 10;

        public static PlayerStat createPlayerStat() {
            return PlayerStat.builder()
                    .playerName(PLAYER_NAME)
                    .playerScore(PLAYER_SCORE)
                    .build();
        }

    }
}

