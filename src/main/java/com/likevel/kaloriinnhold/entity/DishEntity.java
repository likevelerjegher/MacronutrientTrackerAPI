package com.likevel.kaloriinnhold.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "dishes")
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String dishName;
    @Column(name = "fats")
    private Float dishFats = 0.0f;
    @Column(name = "carbs")
    private Float dishCarbs = 0.0f;
    @Column(name = "proteins")
    private Float dishProteins = 0.0f;

    @Column(name = "calories")
    private Integer dishCalories = 0;
    @Column(name = "servings")
    private Float servings = 0.0f;

    @JsonIgnoreProperties("dish")
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<CommentEntity> comments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties("dishes")
    @JoinTable(name = "dish_ingredients",
            joinColumns = @JoinColumn(name = "dishId"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId"))
    private List<IngredientEntity> ingredients;
}
