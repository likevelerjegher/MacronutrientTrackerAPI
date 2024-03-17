package com.likevel.kaloriinnhold.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class CommentEntity {
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
    private DishEntity dish;
}
