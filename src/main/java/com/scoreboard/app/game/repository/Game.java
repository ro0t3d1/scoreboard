package com.scoreboard.app.game.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Game")
public class Game {

    @Id
    @Column(name = "GAME_ID")
    private Long id;

    @Column(name = "GAME_DATE")
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
