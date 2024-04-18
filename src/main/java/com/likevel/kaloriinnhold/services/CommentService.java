package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.exception.ObjectExistedException;
import com.likevel.kaloriinnhold.exception.ObjectNotFoundException;
import com.likevel.kaloriinnhold.model.Comment;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.CommentRepository;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DishRepository dishRepository;

    //Get
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByDishId(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Dish with id " + dishId + " does not exist, therefore cannot view its comments."));

        List<Comment> comments = dish.getComments();

        // If comments are null or empty, return an empty list
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }

        return comments;
    }

    //Post
    public void addNewCommentByDishId(Long dishId, Comment commentRequest) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "dish '" + dishId + "' does not exist, therefore cannot add new comment."));
        if (dish.getComments().stream().noneMatch(comment -> comment.getCommentText().equals(commentRequest.getCommentText()))
                || dish.getComments().stream().noneMatch(comment -> comment.getUsername().equals(commentRequest.getUsername()))) {
            commentRequest.setDish(dish);
            dish.getComments().add(commentRequest);
            commentRepository.save(commentRequest);
            dishRepository.save(dish);
        } else {
            throw new ObjectExistedException("this comment with context \"" + commentRequest.getCommentText()
                    + "\" and username " + commentRequest.getUsername() + "already exists on the dish page.");
        }
    }

    //Put
    @Transactional
    public void updateComment(Long commentId, String username, String commentText) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "comment with id " + commentId + " does not exist, therefore cannot be updated."));

        // Check if commentText is not null and not empty, and it's different from the existing one
        if (commentText != null && !commentText.isEmpty() && !Objects.equals(comment.getCommentText(), commentText)) {
            comment.setCommentText(commentText);
        }

        // Check if username is not null and not empty, and it's different from the existing one
        if (username != null && !username.isEmpty() && !Objects.equals(comment.getUsername(), username)) {
            comment.setUsername(username);
        }

        // Save the updated comment
        commentRepository.save(comment);
    }

    //Delete
    public void deleteCommentsByDishId(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "dish id " + dishId + " does not exist, therefore comments cannot be deleted"));
        dish.getComments().clear();
        dishRepository.save(dish);
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Comment with id " + commentId + " does not exist, therefore cannot be deleted."));

        Dish dish = comment.getDish();
        if (dish != null) {
            dish.getComments().remove(comment); // Remove comment from dish
            dishRepository.save(dish); // Save dish to update the association
        }

        commentRepository.delete(comment); // Delete the comment
    }
}
