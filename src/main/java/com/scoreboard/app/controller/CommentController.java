package com.scoreboard.app.controller;

import com.scoreboard.app.dto.CommentDto;
import com.scoreboard.app.dto.CommentMapper;
import com.scoreboard.app.dto.GameDto;
import com.scoreboard.app.repository.Comment;
import com.scoreboard.app.repository.Game;
import com.scoreboard.app.service.CommentService;
import com.scoreboard.app.util.CommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CommentController {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE = 0;
    private static final String DEFAULT_SORT_PARAMETER = "modifiedDate";

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping(path = "/comment")
    public ResponseEntity<Page<CommentDto>> getComments(
            @RequestParam(required = false) Long gameId,
            @PageableDefault(sort = {DEFAULT_SORT_PARAMETER}, direction = Sort.Direction.DESC, page = DEFAULT_PAGE, value = DEFAULT_PAGE_SIZE)
                    Pageable pageable) {
        Page<Comment> comments = commentService.getComments(CommentSpecification.of(gameId), pageable);
        return ResponseEntity.ok(commentMapper.commentsToCommentsDto(comments));
    }

    @PostMapping(path = "/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        Comment comment = commentService.createComment(commentMapper.commentDtoToComment(commentDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.commentToCommentDto(comment));
    }

    @GetMapping(path = "/comment/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable String commentId) {
        Comment comment = commentService.getComment(commentId);
        return ResponseEntity.ok(commentMapper.commentToCommentDto(comment));
    }

    @PutMapping(path = "/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable String commentId, @RequestBody CommentDto commentDto) {
        Comment comment = commentService.updateComment(commentId, commentMapper.commentDtoToComment(commentDto));
        return ResponseEntity.ok(commentMapper.commentToCommentDto(comment));
    }

    @DeleteMapping(path = "/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/game/{gameId}/comment")
    public ResponseEntity<Page<CommentDto>> getGameComments(
            @PathVariable Long gameId,
            @PageableDefault(sort = {DEFAULT_SORT_PARAMETER}, direction = Sort.Direction.DESC, page = DEFAULT_PAGE, value = DEFAULT_PAGE_SIZE)
                    Pageable pageable) {
        return getComments(gameId, pageable);
    }

}
