package com.likevel.kaloriinnhold.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "ingredients")
public class Ingredient {
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
    private List<Dish> dishes;
}
