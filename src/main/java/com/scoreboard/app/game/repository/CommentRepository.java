package com.scoreboard.app.game.repository;

import com.scoreboard.app.comment.repository.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CommentRepository extends JpaRepository<Comment, String>, JpaSpecificationExecutor<Comment> {

}
