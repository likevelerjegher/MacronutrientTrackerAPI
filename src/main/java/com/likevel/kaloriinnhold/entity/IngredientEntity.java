package com.likevel.kaloriinnhold.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ingredients")
public class IngredientEntity {
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
    @Column(name = "weight")
    private Integer weight;

    @JsonIgnoreProperties("ingredients")
    @ManyToMany(mappedBy = "ingredients",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private List<DishEntity> dishes;
}
