package com.likevel.kaloriinnhold.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "ingredient")
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "fats")
    private Float fats;
    @Column(name = "proteins")
    private Float proteins;
    @Column(name = "carbs")
    private Float carbs;

    @Column(name = "calories")
    private Integer calories;
    @Column(name = "weight")
    private Float weight;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private List<DishEntity> dishes;
}
