package com.scoreboard.app.comment.util;

import com.scoreboard.app.comment.repository.Comment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommentSpecification implements Specification<Comment> {

    private Long gameId;

    private CommentSpecification(Long gameId) {
        this.gameId = gameId;
    }

    @Override
    public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
        if (gameId == null) {
            return cb.isTrue(cb.literal(true));
        }
        return cb.equal(root.get("gameId"), this.gameId);
    }

    public static CommentSpecification of(Long gameId) {
        return new CommentSpecification(gameId);
    }
}
