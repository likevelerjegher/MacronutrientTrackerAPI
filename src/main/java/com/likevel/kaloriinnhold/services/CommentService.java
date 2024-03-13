package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.entity.CommentEntity;
import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.repositories.CommentRepository;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DishRepository dishRepository;

//Get
    public List<CommentEntity> getComments(){
        return commentRepository.findAll();
    }
    public List<CommentEntity> getCommentsByDishId(Long dishId){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish with id " + dishId + " does not exist, therefore cannot view its comments."));
        return dish.getComments();
    }
//Post
    public void addNewCommentByDishId (Long dishId, CommentEntity commentRequest){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish '" + dishId + "' does not exist, therefore cannot add new comment."));
        if (dish.getComments().stream().noneMatch(comment->comment.getCommentText().equals(commentRequest.getCommentText()))){
            dish.getComments().add(commentRequest);
            commentRepository.save(commentRequest);
            dishRepository.save(dish);
        }else{
            throw new IllegalStateException("this comments with context \"" + commentRequest.getCommentText() + "\" already exists on the dish page.");
        }
    }
//Put
    @Transactional
    public void updateComment(Long commentId,
                              String username,
                              String commentText){
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException(
                        "comment with id " + commentId + " does not exist, therefore cannot be updated."));
        if(commentText!=null && commentText.isEmpty() && Objects.equals(comment.getCommentText(), commentText)){
            Optional<CommentEntity> commentOptional = commentRepository.findCommentByCommentText(commentText);
            if(commentOptional.isPresent()){
                throw new IllegalStateException("identical comment already exists.");
            }
            comment.setCommentText(commentText);
        }
        if(username != null && !username.isEmpty() && !Objects.equals(comment.getUsername(),username)){
            comment.setUsername(username);
        }
    }
//Delete
    public void deleteCommentsByDishId(Long dishId){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish id " + dishId + " does not exist, therefore comments cannot be deleted"));
        dish.getComments().clear();
        dishRepository.save(dish);
    }
    public void deleteCommentFromDishId(Long dishId, Long commentId){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish " + dishId + " does not exist, therefore comment cannot be deleted"));
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException(
                        "comment id: " + commentId + " does not exist, therefore cannot be deleted"));
        dish.getComments().remove(comment);
        dishRepository.save(dish);
    }
}
