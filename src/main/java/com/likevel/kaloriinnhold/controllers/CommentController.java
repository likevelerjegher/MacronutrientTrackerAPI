package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.model.Comment;
import com.likevel.kaloriinnhold.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Comment", description = "Managing comments.")
@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //Get
    @GetMapping("/comments")
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/dishes/{dishId}/comments")
    public List<Comment> getCommentsByDishId(@PathVariable(value = "dishId") Long dishId) {
        return commentService.getCommentsByDishId(dishId);
    }

    //Post
    @PostMapping("/dishes/{dishId}/comment")
    public void addNewCommentByDishId(@PathVariable(value = "dishId") Long dishId,
                                      @RequestBody Comment comment) {
        commentService.addNewCommentByDishId(dishId, comment);
    }

    //Put
    @SuppressWarnings("checkstyle:LineLength")
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
    public void deleteComment(@PathVariable("id") Long dishId) {
        commentService.deleteComment(dishId);

    }
}
