package com.scoreboard.app.game;

import com.scoreboard.app.comment.dto.CommentMapper;
import com.scoreboard.app.comment.service.CommentService;
import com.scoreboard.app.exception.ApplicationException;
import com.scoreboard.app.game.dto.GameMapper;
import com.scoreboard.app.game.service.GameService;
import com.scoreboard.app.game.service.GameStatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@WebMvcTest
@ExtendWith(SpringExtension.class)
@MockBeans({@MockBean(GameMapper.class), @MockBean(CommentService.class), @MockBean(CommentMapper.class), @MockBean(GameStatsService.class)})
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private GameService gameService;

    @PostConstruct()
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void getMappingWithoutRequiredGameDateParameterResultBadRequest() throws Exception {
        Mockito.when(gameService.getGamesByDate(null)).thenThrow(new ApplicationException("query.parameter.mandatory", "date"));
        mockMvc.perform(get("/game")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @TestConfiguration
    @AutoConfigureDataJpa
    @ComponentScan(basePackageClasses = GameController.class, useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = GameController.class)
            }
    )
    public static class TestConfig {

    }

}
