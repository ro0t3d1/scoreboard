package com.scoreboard.app.comment.dto;

import com.scoreboard.app.comment.repository.Comment;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;


@Mapper
public interface CommentMapper {

    CommentDto commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDto commentDto);

    default Page<CommentDto> commentsToCommentsDto(Page<Comment> comments) {
        return comments.map(this::commentToCommentDto);
    }

    default Page<Comment> commentsDtoToComments(Page<CommentDto> commentsDto) {
        return commentsDto.map(this::commentDtoToComment);
    }
}
