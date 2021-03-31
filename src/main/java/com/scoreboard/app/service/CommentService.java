package com.scoreboard.app.service;

import com.scoreboard.app.repository.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface CommentService {

    Page<Comment> getComments(Specification specification, Pageable pageable);

    Comment getComment(String commentId);

    Comment createComment(Comment comment);

    Comment updateComment(String commentId, Comment comment);

    void deleteComment(String commentId);
}
