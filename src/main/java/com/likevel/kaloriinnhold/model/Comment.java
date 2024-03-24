package com.likevel.kaloriinnhold.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "commentText")
    private String commentText;

    @ManyToOne
    @JoinColumn(name = "dishId")
    @JsonIgnoreProperties("comments")
    private Dish dish;
}
