package com.scoreboard.app.rapidapi;

import com.scoreboard.app.rapidapi.dto.RapidApiGameDto;
import com.scoreboard.app.rapidapi.dto.RapidApiPagedResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class RapidApiHttpClient {

    private final RestTemplate restTemplate;
    private final RapidApiResourcesFactory rapidApiResourcesFactory;

    @Autowired
    public RapidApiHttpClient(RestTemplate restTemplate, RapidApiResourcesFactory rapidApiResourcesFactory) {
        this.restTemplate = restTemplate;
        this.rapidApiResourcesFactory = rapidApiResourcesFactory;
    }

    public RapidApiGameDto getGame(Long gameId) {
        ResponseEntity<RapidApiGameDto> responseEntity = restTemplate.getForEntity(
                rapidApiResourcesFactory.createResourceUrl(String.format(RapidApiResource.GAME.getPath(), gameId)),
                RapidApiGameDto.class
        );
        return responseEntity.getBody();
    }

    public List<RapidApiGameDto> getGames(String date) {
        List<RapidApiGameDto> rapidApiGames = new ArrayList<>();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(rapidApiResourcesFactory.createResourceUrl(RapidApiResource.GAMES.getPath()))
                .queryParam("dates[]", date);
        RapidApiPagedResponseDto<RapidApiGameDto> responseEntity;
        int pageCount = 0;
        do {
            uriBuilder.queryParam("page", String.valueOf(++pageCount));
            responseEntity = restTemplate.exchange(
                    uriBuilder.build(false).toUriString(),
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<RapidApiPagedResponseDto<RapidApiGameDto>>() {
                    }).getBody();
            if (responseEntity != null && responseEntity.getData() != null && !responseEntity.getData().isEmpty()) {
                rapidApiGames.addAll(responseEntity.getData());
            }
        }
        while (responseEntity != null && responseEntity.getMeta() != null && responseEntity.getMeta().getNextPage() != null);
        return rapidApiGames;
    }
}
