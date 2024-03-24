package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.cache.CacheManager;
import com.likevel.kaloriinnhold.model.Comment;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.CommentRepository;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DishRepository dishRepository;
    private static final String ALL_COMMENTS_REQUEST = "http://localhost:8080/api/comments";
    private static final String COMMENTS_BY_ID_REQUEST = "http://localhost:8080/api/dishes/";

    //Get
    public List<Comment> getComments() {
        if (CacheManager.containsKey(ALL_COMMENTS_REQUEST)) {
            return (List<Comment>) CacheManager.get(ALL_COMMENTS_REQUEST);
        } else {
            List<Comment> comments = commentRepository.findAll();
            CacheManager.put(ALL_COMMENTS_REQUEST, comments);
            return comments;
        }
    }

    public List<Comment> getCommentsByDishId(Long dishId) {
        if (CacheManager.containsKey(COMMENTS_BY_ID_REQUEST + dishId + "/comments")) {
            return (List<Comment>) CacheManager.get(COMMENTS_BY_ID_REQUEST + dishId + "/comments");
        } else {
            Dish dish = dishRepository.findById(dishId).orElse(null);
            List<Comment> comments = dish.getComments();
            CacheManager.put(COMMENTS_BY_ID_REQUEST + dishId + "/comments", comments);
            return comments;
        }
    }

    //Post
    public void addNewCommentByDishId(Long dishId, Comment commentRequest) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish '" + dishId + "' does not exist, therefore cannot add new comment."));
        if (dish.getComments().stream().noneMatch(comment -> comment.getCommentText().equals(commentRequest.getCommentText()))
                || dish.getComments().stream().noneMatch(comment -> comment.getUsername().equals(commentRequest.getUsername()))) {
            commentRequest.setDish(dish);
            dish.getComments().add(commentRequest);
            commentRepository.save(commentRequest);
            dishRepository.save(dish);
            CacheManager.clear();
        } else {
            throw new IllegalStateException("this comment with context \"" + commentRequest.getCommentText()
                    + "\" and username " + commentRequest.getUsername() + "already exists on the dish page.");
        }
    }

    //Put
    @Transactional
    public void updateComment(Long commentId,
                              String username,
                              String commentText) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException(
                        "comment with id " + commentId + " does not exist, therefore cannot be updated."));
        if (commentText != null && commentText.isEmpty() && Objects.equals(comment.getCommentText(), commentText)) {
            Optional<Comment> commentOptional = commentRepository.findCommentByCommentText(commentText);
            if (commentOptional.isPresent()) {
                throw new IllegalStateException("identical comment already exists.");
            }
            comment.setCommentText(commentText);
        }
        if (username != null && !username.isEmpty() && !Objects.equals(comment.getUsername(), username)) {
            comment.setUsername(username);
        }
        CacheManager.clear();
    }

    //Delete
    public void deleteCommentsByDishId(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish id " + dishId + " does not exist, therefore comments cannot be deleted"));
        dish.getComments().clear();
        dishRepository.save(dish);
        CacheManager.clear();
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException(
                        "comment with id " + commentId + " does not exist, therefore cannot be deleted."));
        Dish dish = comment.getDish();

        dish.getComments().remove(comment);
        dishRepository.save(dish);

        commentRepository.delete(comment);
        CacheManager.clear();
    }
}