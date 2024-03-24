package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findAll();

    Optional<Comment> findCommentByCommentText(String commentText);
}
