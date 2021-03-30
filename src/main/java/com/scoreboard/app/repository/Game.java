package com.scoreboard.app.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game implements Serializable {

    private Long gameId;
    // TODO: This probably must be a ZoneDateTime.
    private String date;
    private String homeTeamName;
    private Integer homeTeamScore;
    private String visitorTeamName;
    private Integer visitorTeamScore;

}
