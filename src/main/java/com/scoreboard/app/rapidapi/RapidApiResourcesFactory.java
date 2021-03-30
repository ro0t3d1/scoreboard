package com.scoreboard.app.rapidapi;

import com.scoreboard.app.configuration.RapidApiConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RapidApiResourcesFactory {

    @Autowired
    private RapidApiConfiguration rapidApiConfiguration;

    public String createResourceUrl(String path) {
        return String.format("https://%s%s",
                rapidApiConfiguration.getRapidApiHost(),
                path
        );
    }

}
