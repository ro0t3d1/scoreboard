package com.scoreboard.app.game.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game implements Serializable {

    private Long id;
    private String date;
    private String homeTeamName;
    private Integer homeTeamScore;
    private String visitorTeamName;
    private Integer visitorTeamScore;

}
