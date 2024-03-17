package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.entity.CommentEntity;
import com.likevel.kaloriinnhold.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //Get
    @GetMapping("/comments")
    public List<CommentEntity> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/dishes/{dishId}/comments")
    public List<CommentEntity> getCommentsByDishId(@PathVariable(value = "dishId") Long dishId) {
        return commentService.getCommentsByDishId(dishId);
    }

    //Post
    @PostMapping("/dishes/{dishId}/comment")
    public void addNewCommentByDishId(@PathVariable(value = "dishId") Long dishId,
                                      @RequestBody CommentEntity comment) {
        commentService.addNewCommentByDishId(dishId, comment);
    }

    //Put
    @PutMapping("/comments/{id}")
    public void updateComment(@PathVariable("id") Long commentId,
                              @RequestParam(required = false) String username,
                              @RequestParam(required = false) String commentText) {
        commentService.updateComment(commentId, username, commentText);
    }

    //Delete
    @DeleteMapping("/dishes/{dishId}/comments")
    public void deleteCommentsByDishId(@PathVariable(value = "dishId") Long dishId) {
        commentService.deleteCommentsByDishId(dishId);
    }

    @DeleteMapping("/comments/{id}")
    public void deleteDish(@PathVariable("id") Long dishId) {
        commentService.deleteComment(dishId);

    }
}
