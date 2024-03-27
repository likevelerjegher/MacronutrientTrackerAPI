package com.likevel.kaloriinnhold.services;

import jakarta.persistence.EntityNotFoundException;
import com.likevel.kaloriinnhold.model.Comment;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.CommentRepository;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DishRepository dishRepository;
    static final Logger LOGGER = LogManager.getLogger(CommentService.class);

    //Get
    public List<Comment> getComments() {
        LOGGER.info("Retrieving all the ingredients from DB.");
        return commentRepository.findAll();
    }

    public List<Comment> getCommentsByDishId(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish with id " + dishId + " does not exist, therefore cannot view its comments."));
        LOGGER.info("Comments from dish id have been retrieved.");
        return dish.getComments();
    }

    //Post
    public void addNewCommentByDishId(Long dishId, Comment commentRequest) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish '" + dishId + "' does not exist, therefore cannot add new comment."));
        if (dish.getComments().stream().noneMatch(comment -> comment.getCommentText().equals(commentRequest.getCommentText()))
                || dish.getComments().stream().noneMatch(comment -> comment.getUsername().equals(commentRequest.getUsername()))) {
            commentRequest.setDish(dish);
            dish.getComments().add(commentRequest);
            commentRepository.save(commentRequest);
            dishRepository.save(dish);
            LOGGER.info("New comment to the dish has been added.");
        } else {
            LOGGER.info("New comment to the dish hasn't been added.");
            throw new EntityNotFoundException("this comment with context \"" + commentRequest.getCommentText()
                    + "\" and username " + commentRequest.getUsername() + "already exists on the dish page.");
        }
    }

    //Put
    @Transactional
    public void updateComment(Long commentId,
                              String username,
                              String commentText) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "comment with id " + commentId + " does not exist, therefore cannot be updated."));
        if (commentText != null && commentText.isEmpty() && Objects.equals(comment.getCommentText(), commentText)) {
            Optional<Comment> commentOptional = commentRepository.findCommentByCommentText(commentText);
            if (commentOptional.isPresent()) {
                LOGGER.info("The comment was not updated.");
                throw new IllegalStateException("identical comment already exists.");
            }
            comment.setCommentText(commentText);
        }
        if (username != null && !username.isEmpty() && !Objects.equals(comment.getUsername(), username)) {
            comment.setUsername(username);
        }
        LOGGER.info("The comment was updated.");
    }

    //Delete
    public void deleteCommentsByDishId(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish id " + dishId + " does not exist, therefore comments cannot be deleted"));
        dish.getComments().clear();
        dishRepository.save(dish);
        LOGGER.info("The comment from the dish has been deleted.");
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "comment with id " + commentId + " does not exist, therefore cannot be deleted."));
        Dish dish = comment.getDish();

        dish.getIngredients().remove(comment);
        dishRepository.save(dish);
        commentRepository.delete(comment);
        LOGGER.info("The comment has been deleted.");
    }

}
