package com.scoreboard.app.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoreboard.app.comment.dto.CommentDto;
import com.scoreboard.app.comment.dto.CommentMapper;
import com.scoreboard.app.comment.service.CommentService;
import com.scoreboard.app.exception.ResourceNotFoundException;
import com.scoreboard.app.game.dto.GameMapper;
import com.scoreboard.app.game.service.GameService;
import com.scoreboard.app.game.service.GameStatsService;
import com.scoreboard.app.utils.EntityCreator;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@WebMvcTest
@ExtendWith(SpringExtension.class)
@MockBeans({@MockBean(CommentMapper.class), @MockBean(GameService.class), @MockBean(GameMapper.class), @MockBean(GameStatsService.class)})
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct()
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createCommentReturnCreated() throws Exception {
        CommentDto commentDto = EntityCreator.CommentCreator.createCommentDto();
        commentDto.setModifiedDate(null);
        Mockito.when(commentService.createComment(any())).thenReturn(EntityCreator.CommentCreator.createComment());
        mockMvc.perform(post("/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(commentDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getCommentWithInvalidIdReturnNotFoundException() throws Exception {
        Mockito.when(commentService.getComment(any())).thenThrow(new ResourceNotFoundException());
        mockMvc.perform(get("/comment/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCommentWithValidIdReturnNoContent() throws Exception {
        mockMvc.perform(delete("/comment/123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @TestConfiguration
    @AutoConfigureDataJpa
    @ComponentScan(basePackageClasses = CommentController.class, useDefaultFilters = false,
            includeFilters = {
                    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommentController.class)
            }
    )
    public static class TestConfig {

    }

}