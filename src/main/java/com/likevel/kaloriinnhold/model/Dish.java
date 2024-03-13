package com.likevel.kaloriinnhold.model;

import com.likevel.kaloriinnhold.entity.DishEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Dish {
    private Long id;
    private String name;

    private Float fats;
    private Float carbs;
    private Float proteins;

    private Integer calories;
    private Integer servings;
    private List<Ingredient> ingredients;

    public static Dish toModel(DishEntity entity){
        Dish model = new Dish();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setFats(entity.getFats());
        model.setCarbs(entity.getCarbs());
        model.setProteins(entity.getProteins());
        model.setCalories(entity.getCalories());
        model.setServings(entity.getServings());
        model.setIngredients(entity.getIngredients().stream().map(Ingredient::toModel).collect(Collectors.toList()));
        return model;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getFats() {
        return fats;
    }

    public void setFats(Float fats) {
        this.fats = fats;
    }

    public Float getProteins() {
        return proteins;
    }

    public void setProteins(Float proteins) {
        this.proteins = proteins;
    }

    public Float getCarbs() {
        return carbs;
    }

    public void setCarbs(Float carbs) {
        this.carbs = carbs;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }
}
