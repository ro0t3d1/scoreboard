package com.scoreboard.app.rapidapi;

public enum RapidApiResource {
    GAMES("/games"),
    GAME("/games/%d"),
    STATS("/stats"),
    ;

    private String path;

    RapidApiResource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
