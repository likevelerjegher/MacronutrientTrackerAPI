package com.likevel.kaloriinnhold.service;

import com.likevel.kaloriinnhold.model.Comment;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.CommentRepository;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getComments() {
        List<Comment> comments = new ArrayList<>();
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> result = commentService.getComments();

        assertEquals(comments, result);
    }

    @Test
    void getCommentsByDishId() {
        // Arrange
        Long dishId = 1L;
        Dish dish = new Dish();
        dish.setId(dishId);

        // Mock the behavior of the dishRepository to return the dish when findById is called
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Create some comments associated with the dish
        Comment comment1 = new Comment();
        comment1.setDish(dish);
        dish.getComments().add(comment1);

        Comment comment2 = new Comment();
        comment2.setDish(dish);
        dish.getComments().add(comment2);

        // Mock the behavior of the commentRepository to return all comments
        List<Comment> allComments = List.of(comment1, comment2);
        when(commentRepository.findAll()).thenReturn(allComments);

        // Act
        List<Comment> result = commentService.getCommentsByDishId(dishId);

        // Assert
        // Verify that the result contains the expected number of comments
        assertEquals(2, result.size()); // Ensure that the result contains two comments
        assertTrue(result.contains(comment1)); // Check if comment1 is in the result
        assertTrue(result.contains(comment2)); // Check if comment2 is in the result
    }

    @Test
    void addNewCommentByDishId() {
        Long dishId = 1L;
        Dish dish = new Dish();
        dish.setId(dishId);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        Comment commentRequest = new Comment();
        commentRequest.setDish(dish);

        commentService.addNewCommentByDishId(dishId, commentRequest);

        verify(commentRepository, times(1)).save(commentRequest);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void updateComment() {
        Long commentId = 1L;
        String username = "user";
        String commentText = "new comment";

        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setUsername("oldUser");
        comment.setCommentText("old comment");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.updateComment(commentId, username, commentText);

        assertEquals(username, comment.getUsername());
        assertEquals(commentText, comment.getCommentText());
    }

    @Test
    void deleteCommentsByDishId() {
        Long dishId = 1L;
        Dish dish = new Dish();
        dish.setId(dishId);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        commentService.deleteCommentsByDishId(dishId);

        assertTrue(dish.getComments().isEmpty());
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void deleteComment() {
        Long commentId = 1L;

        Comment comment = new Comment();
        comment.setId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        commentService.deleteComment(commentId);

        verify(commentRepository, times(1)).delete(comment);
    }
}
