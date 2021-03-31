package com.scoreboard.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CommentRepository extends JpaRepository<Comment, String>, JpaSpecificationExecutor<Comment> {

}
