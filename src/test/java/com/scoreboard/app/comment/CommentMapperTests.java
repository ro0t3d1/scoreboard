package com.scoreboard.app.comment;

import com.scoreboard.app.comment.dto.CommentDto;
import com.scoreboard.app.comment.dto.CommentMapper;
import com.scoreboard.app.comment.repository.Comment;
import com.scoreboard.app.utils.EntityCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class CommentMapperTests {

    @Spy
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void shouldReturnCommentDto() {
        Comment comment = EntityCreator.CommentCreator.createComment();
        CommentDto commentDto = commentMapper.commentToCommentDto(comment);
        assertThat(commentDto.getId()).isEqualTo(comment.getId());
        assertThat(commentDto.getGameId()).isEqualTo(comment.getGameId());
        assertThat(commentDto.getText()).isEqualTo(comment.getText());
        assertThat(commentDto.getModifiedDate()).isEqualTo(comment.getModifiedDate());
    }

    @Test
    void shouldReturnComment() {
        CommentDto commentDto = EntityCreator.CommentCreator.createCommentDto();
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        assertThat(commentDto.getId()).isEqualTo(comment.getId());
        assertThat(commentDto.getGameId()).isEqualTo(comment.getGameId());
        assertThat(commentDto.getText()).isEqualTo(comment.getText());
        assertThat(commentDto.getModifiedDate()).isEqualTo(comment.getModifiedDate());
    }

}
