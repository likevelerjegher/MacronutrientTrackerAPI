package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    List<CommentEntity> findAll();

    Optional<CommentEntity> findCommentByCommentText(String commentText);
}
