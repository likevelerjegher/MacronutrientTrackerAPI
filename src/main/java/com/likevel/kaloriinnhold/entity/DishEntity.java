package com.likevel.kaloriinnhold.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "dish")
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "fats")
    private Float fats = 0.0f;
    @Column(name = "carbs")
    private Float carbs = 0.0f;
    @Column(name = "proteins")
    private Float proteins = 0.0f;

    @Column(name = "calories")
    private Integer calories = 0;
    @Column(name = "servings")
    private Float servings = 0.0f;

    @OneToMany(mappedBy = "dish", orphanRemoval=true, cascade = CascadeType.ALL)
    private List<CommentEntity> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dish_ingredients",
            joinColumns = {@JoinColumn(name = "dish_id")},
            inverseJoinColumns = {@JoinColumn(name = "ingredient_id")})
    private List<IngredientEntity> ingredients;
}
