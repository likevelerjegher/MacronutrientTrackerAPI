package com.likevel.kaloriinnhold.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
public class Dish {
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
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties("dishes")
    @JoinTable(name = "dish_ingredients",
            joinColumns = @JoinColumn(name = "dishId"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId"))
    private List<Ingredient> ingredients;
}
