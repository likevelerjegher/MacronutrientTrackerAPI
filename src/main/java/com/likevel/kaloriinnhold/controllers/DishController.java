package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.services.DishService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DishController {
    //Get
    private final DishService dishService;

    @GetMapping("/calculate")
    public ResponseEntity<Object> getDishesWithParams(@RequestParam(name = "name") String name,
                                                      @RequestParam(name = "weight") Long weight) {
        return new ResponseEntity<>(dishService.getNutritionalData(name, weight), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public DishEntity getDishById(@PathVariable("id") Long dishId) {
        return dishService.getDishById(dishId);
    }

    @GetMapping("/dishes")
    public List<DishEntity> getDishes() {
        return dishService.getDishes();
    }

    //Post
    @PostMapping
    public void createNewDish(@RequestBody DishEntity dish) {
        dishService.createNewDish(dish);
    }

    //Put
    @PutMapping("/{id}")
    public void updateDish(@PathVariable("id") Long dishId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) Float servings) {
        dishService.updateDish(dishId, name, servings);
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