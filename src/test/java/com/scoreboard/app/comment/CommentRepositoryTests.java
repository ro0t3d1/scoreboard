package com.scoreboard.app.comment;

import com.scoreboard.app.comment.repository.Comment;
import com.scoreboard.app.comment.repository.CommentRepository;
import com.scoreboard.app.utils.EntityCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentRepositoryTests {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void givenCommentToAddShouldReturnAddedComment() {
        Comment saveComment = commentRepository.save(EntityCreator.CommentCreator.createComment());
        Comment fetchedComment = commentRepository.findById(saveComment.getId()).get();
        assertEquals(saveComment.getId(), fetchedComment.getId());
        assertEquals(saveComment.getText(), fetchedComment.getText());
        assertEquals(saveComment.getGameId(), fetchedComment.getGameId());
    }

    @Test
    public void givenGetAllCommentsShouldReturnListOfAllComments() {
        Comment comment1 = EntityCreator.CommentCreator.createComment();
        Comment comment2 = EntityCreator.CommentCreator.createComment();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        List<Comment> comments = commentRepository.findAll();
        assertEquals(2, comments.size());
    }

    @Test
    public void givenIdToDeleteThenShouldDeleteTheComment() {
        Comment saveComment = commentRepository.save(EntityCreator.CommentCreator.createComment());
        commentRepository.deleteById(saveComment.getId());
        Optional optional = commentRepository.findById(saveComment.getId());
        assertEquals(Optional.empty(), optional);
    }

    @AfterEach
    public void destroy() {
        commentRepository.deleteAll();
    }

}
