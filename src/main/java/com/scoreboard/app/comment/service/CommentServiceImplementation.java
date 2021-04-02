package com.scoreboard.app.comment.service;

import com.google.common.base.Strings;
import com.scoreboard.app.exception.ApplicationException;
import com.scoreboard.app.exception.ResourceNotFoundException;
import com.scoreboard.app.game.service.GameService;
import com.scoreboard.app.comment.repository.Comment;
import com.scoreboard.app.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CommentServiceImplementation implements CommentService {

    private final CommentRepository commentRepository;

    private final GameService gameService;

    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository, GameService gameService) {
        this.commentRepository = commentRepository;
        this.gameService = gameService;
    }

    @Override
    public Page<Comment> getComments(Specification specification, Pageable pageable) {
        return commentRepository.findAll(specification, pageable);
    }

    @Override
    public Comment getComment(String commentId) {
        Optional<Comment> optionalGameComment = commentRepository.findById(commentId);
        if (!optionalGameComment.isPresent()) {
            throw new ResourceNotFoundException("comment", commentId);
        }
        return optionalGameComment.get();
    }

    @Override
    public Comment createComment(Comment comment) {
        validateCommentOnCreation(comment);
        comment.setId(null);
        comment.setModifiedDate(ZonedDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(String commentId, Comment comment) {
        validateGameCommentOnUpdate(comment);
        Comment savedComment = getComment(commentId);
        savedComment.setText(comment.getText());
        savedComment.setModifiedDate(ZonedDateTime.now());
        return commentRepository.save(savedComment);
    }

    @Override
    public void deleteComment(String commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    private void validateCommentOnCreation(Comment comment) {
        if (Strings.isNullOrEmpty(comment.getText())) {
            throw new ApplicationException("parameter.mandatory", "text");
        }
        if (comment.getGameId() == null) {
            throw new ApplicationException("parameter.mandatory", "gameId");
        }
        if (comment.getText().length() > Comment.MAXIMUM_TEXT_LENGTH) {
            throw new ApplicationException("parameter.toLong", "text", Comment.MAXIMUM_TEXT_LENGTH);
        }
        // Only required  to validate if game exists.
        gameService.getGame(comment.getGameId());
    }

    private void validateGameCommentOnUpdate(Comment comment) {
        if (Strings.isNullOrEmpty(comment.getText())) {
            throw new ApplicationException("parameter.mandatory", "text");
        }
    }
}
