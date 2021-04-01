package com.scoreboard.app.comment.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CommentDto {

    private String id;
    private Long gameId;
    private String text;
    private ZonedDateTime modifiedDate;
}
