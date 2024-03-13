package com.likevel.kaloriinnhold.model;

import com.likevel.kaloriinnhold.entity.IngredientEntity;

public class Ingredient {
    private Long id;

    private String name;

    private Float fats;
    private Float carbs;
    private Float proteins;

    private Integer calories;
    private Float weight;

    public static Ingredient toModel(IngredientEntity entity){
        Ingredient model = new Ingredient();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setFats(entity.getFats());
        model.setCarbs(entity.getCarbs());
        model.setProteins(entity.getProteins());
        model.setCalories(entity.getCalories());
        model.setWeight(entity.getWeight());
        return model;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
