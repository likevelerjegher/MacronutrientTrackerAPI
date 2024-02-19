package com.likevel.kaloriinnhold.dish;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "dishes")
public class Dish {
    @Id
    private ObjectId id;
    private String name;
    private Float fats;
    private Float proteins;
    private Float carbs;
    private Integer calories;

    public Dish() {
    }

    public Dish(ObjectId id, String name, Float fats, Float proteins, Float carbs, Integer calories) {
        this.id = id;
        this.name = name;
        this.fats = fats;
        this.proteins = proteins;
        this.carbs = carbs;
        this.calories = calories;
    }

    public Dish(String name, Float fats, Float carbs, Float proteins, Integer calories) {
        this.name = name;
        this.fats = fats;
        this.proteins = proteins;
        this.carbs = carbs;
        this.calories = calories;
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

}
