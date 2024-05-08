package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.services.DishService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Dish", description = "Managing dishes.")

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
public class DishController {
    //Get
    private final DishService dishService;

    @GetMapping("/{id}")
    public Dish getDishById(@PathVariable("id") Long dishId) {
        //try
        return dishService.getDishById(dishId);
    }

    @GetMapping("/dishes")
    public List<Dish> getDishes() {
        return dishService.getDishes();
    }

    @GetMapping("/limit")
    public List<Dish> getDishesWithLessOrSameCalories(@RequestParam("calories") Integer calorieLimit) {
        return dishService.getDishesWithLessOrSameCalories(calorieLimit);
    }

    //Post
    @PostMapping
    public void createNewDish(@RequestBody Dish dish) {
        dishService.createNewDish(dish);
    }

    @PostMapping("/addDishes")
    public List<Dish> saveDishes(
            @RequestBody final List<Dish> dishes) {
        return dishService.saveDishes(dishes);
    }
    //Put
    @PutMapping("/update/{id}")
    public void updateDish(@PathVariable("id") Long dishId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) Float servings) {
        dishService.updateDish(dishId, name, servings);
    }
    @PutMapping("/{id}")
    public Dish editDish(@RequestBody Dish newDish, @PathVariable("id") Long dishId) {
        return dishService.editDish(newDish, dishId);
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable("id") Long dishId) {
        dishService.deleteDish(dishId);
    }

    @DeleteMapping("/dishes")
    public void deleteDishes() {
        dishService.deleteDishes();
    }

}