package com.scoreboard.app.comment;

import com.scoreboard.app.comment.repository.Comment;
import com.scoreboard.app.comment.repository.CommentRepository;
import com.scoreboard.app.comment.service.CommentServiceImplementation;
import com.scoreboard.app.comment.util.CommentSpecification;
import com.scoreboard.app.exception.ApplicationException;
import com.scoreboard.app.exception.ResourceNotFoundException;
import com.scoreboard.app.game.repository.Game;
import com.scoreboard.app.game.service.GameService;
import com.scoreboard.app.utils.EntityCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private GameService gameService;

    @InjectMocks
    private CommentServiceImplementation commentService;

    @Test
    void givenCommentToAddShouldReturnAddedComment() {
        Comment comment = EntityCreator.CommentCreator.createComment();
        when(gameService.getGame(any())).thenReturn(new Game());
        when(commentRepository.save(any())).thenReturn(comment);
        Comment createdComment = commentService.createComment(comment);
        assertEquals(comment.getText(), createdComment.getText());
        assertEquals(comment.getGameId(), createdComment.getGameId());
        verify(commentRepository, times(1)).save(any());
    }

    @Test
    void givenGetAllCommentsShouldReturnListOfAllComments(){
        CommentSpecification spec = CommentSpecification.of(null);
        PageRequest pageRequest = PageRequest.of(0, 20);
        Comment savedComment = EntityCreator.CommentCreator.createComment();
        when(commentRepository.save(any())).thenReturn(savedComment);
        when(gameService.getGame(any())).thenReturn(new Game());
        List<Comment> comments = new ArrayList<>();
        comments.add(savedComment);
        PageImpl<Comment> createComments = new PageImpl<>(comments);
        when(commentRepository.findAll(spec, pageRequest)).thenReturn(createComments);
        Page<Comment> returnComments = commentService.getComments(spec, pageRequest);
        assertEquals(returnComments, createComments);
    }

    @Test
    void givenCommentWithNoTextReturnApplicationException() {
        when(gameService.getGame(any())).thenReturn(new Game());
        Comment comment = EntityCreator.CommentCreator.createComment();
        comment.setText(null);
        ApplicationException exception = assertThrows(ApplicationException.class, () -> commentService.createComment(comment));
        String exceptedCode = "parameter.mandatory";
        String actualCode = exception.getCode();
        assertEquals(exceptedCode, actualCode);
    }

    @Test
    void givenCommentWithTextToLongReturnApplicationException() {
        when(gameService.getGame(any())).thenReturn(new Game());
        String generatedString = IntStream.range(0, 501)
                .mapToObj(i ->"x")
                .collect(Collectors.joining());
        Comment comment = EntityCreator.CommentCreator.createComment();
        comment.setText(generatedString);
        ApplicationException exception = assertThrows(ApplicationException.class, () -> commentService.createComment(comment));
        String exceptedCode = "parameter.toLong";
        String actualCode = exception.getCode();
        assertEquals(exceptedCode, actualCode);
    }

    @Test
    void givenCommentWithInvalidGameIdReturnResourceNotFoundException() {
        when(gameService.getGame(any())).thenThrow(new ResourceNotFoundException());
        Comment dummyComment = EntityCreator.CommentCreator.createComment();
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> commentService.createComment(dummyComment));
        String exceptedCode = "resource.notFound";
        String actualCode = exception.getCode();
        assertEquals(exceptedCode, actualCode);
    }


}
