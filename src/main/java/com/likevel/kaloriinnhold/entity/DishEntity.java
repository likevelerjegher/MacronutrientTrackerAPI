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
    private Float fats;
    @Column(name = "carbs")
    private Float carbs;
    @Column(name = "proteins")
    private Float proteins;

    @Column(name = "calories")
    private Integer calories;
    @Column(name = "servings")
    private Float servings;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dish_id")
    private List<CommentEntity> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dish_ingredients",
    joinColumns = {@JoinColumn(name = "dish_id")},
    inverseJoinColumns = {@JoinColumn(name = "ingredient_id")})
    private List<IngredientEntity> ingredients;
}
