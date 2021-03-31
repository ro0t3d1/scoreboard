package com.scoreboard.app.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game implements Serializable {

    @Id
    @Column(name = "GAME_ID")
    private Long gameId;

    // TODO: This probably must be a ZoneDateTime.
    @Column(name = "GAME_ID")
    private String date;

    @Column(name = "HOME_TEAM_NAME")
    private String homeTeamName;

    @Column(name = "HOME_TEAM_SCORE")
    private Integer homeTeamScore;

    @Column(name = "VISITOR_TEAM_NAME")
    private String visitorTeamName;

    @Column(name = "VISITOR_TEAM_SCORE")
    private Integer visitorTeamScore;

}
