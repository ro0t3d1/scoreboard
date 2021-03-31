package com.scoreboard.app.rapidapi;

import com.scoreboard.app.rapidapi.dto.RapidApiGameDto;
import com.scoreboard.app.rapidapi.dto.RapidApiPagedResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RapidApiHttpClient {

    private final RestTemplate restTemplate;
    private final RapidApiResourcesFactory rapidApiResourcesFactory;

    @Autowired
    public RapidApiHttpClient(RestTemplate restTemplate, RapidApiResourcesFactory rapidApiResourcesFactory) {
        this.restTemplate = restTemplate;
        this.rapidApiResourcesFactory = rapidApiResourcesFactory;
    }

    public Optional<RapidApiGameDto> getGame(Long gameId) {
        try {
            RapidApiGameDto rapidApiGameDto = restTemplate.getForEntity(
                    rapidApiResourcesFactory.createResourceUrl(String.format(RapidApiResource.GAME.getPath(), gameId)),
                    RapidApiGameDto.class
            ).getBody();
            return Optional.of(rapidApiGameDto);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                log.warn("Could not find game matching with id '{}'", gameId);
                return Optional.empty();
            }
            log.error("Unable to obtain game with id '{}'", gameId, e);
            throw e;
        }
    }

    public List<RapidApiGameDto> getGamesByDate(String date) {
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
                    new ParameterizedTypeReference<RapidApiPagedResponseDto<RapidApiGameDto>>() {}
                    ).getBody();
            if (responseEntity != null && responseEntity.getData() != null && !responseEntity.getData().isEmpty()) {
                rapidApiGames.addAll(responseEntity.getData());
            }
        }
        while (responseEntity != null && responseEntity.getMeta() != null && responseEntity.getMeta().getNextPage() != null);
        return rapidApiGames;
    }
}
