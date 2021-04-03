package com.scoreboard.app.game;

import com.scoreboard.app.exception.ApplicationException;
import com.scoreboard.app.exception.ResourceNotFoundException;
import com.scoreboard.app.game.service.GameServiceImplementation;
import com.scoreboard.app.rapidapi.RapidApiHttpClient;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
class GameServiceTest {

    @Mock
    private RapidApiHttpClient rapidApiHttpClient;

    @InjectMocks
    private GameServiceImplementation gameService;

    @Test
    void givenGameWithInvalidIdReturnResourceNotFoundException() {
        Mockito.when(rapidApiHttpClient.getGame(any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> gameService.getGame(1L));
    }

    @Test
    void givenSearchGamesByDateWithNoDateReturnApplicationException() {
        ApplicationException exception = assertThrows(ApplicationException.class, () -> gameService.getGamesByDate(null));
        String expectedCode = "query.parameter.mandatory";
        String actualCode = exception.getCode();
        Assert.assertEquals(expectedCode, actualCode);
    }

}
