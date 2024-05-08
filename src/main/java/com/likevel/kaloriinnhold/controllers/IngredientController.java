package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.model.Ingredient;
import com.likevel.kaloriinnhold.services.IngredientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "Ingredient", description = "Managing ingredients.")
@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    //Get
    @GetMapping("ingredients/{id}")
    public Ingredient getIngredientById(@PathVariable(value = "id") Long ingredientId) {
        return ingredientService.getIngredientById(ingredientId);
    }
    @GetMapping("dishes/{dishId}/ingredients")
    public List<Ingredient> getIngredientsByDishId(@PathVariable(value = "dishId") Long dishId) {
        return ingredientService.getIngredientsByDishId(dishId);
    }

    @GetMapping("ingredients/{ingredientId}/dishes")
    public List<Dish> getDishesByIngredientId(@PathVariable(value = "ingredientId") Long ingredientId) {
        return ingredientService.getDishesByIngredientId(ingredientId);
    }

    @GetMapping("/ingredients")
    public List<Ingredient> getIngredients() {
        return ingredientService.getIngredients();
    }

    //Post
    @PostMapping("/ingredient")
    public Ingredient createNewIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.createNewIngredient(ingredient);
    }

    @PostMapping("dishes/{dishId}/ingredient")
    public void addNewIngredientByDishId(@PathVariable(value = "dishId") Long dishId,
                                         @RequestBody Ingredient ingredient) {
        ingredientService.addNewIngredientByDishId(dishId, ingredient);
    }

    @PostMapping("/dishes/{dishId}/ingredients/{ingredientId}")
    public void addExistingIngredientByDishId(@PathVariable(value = "dishId") Long dishId,
                                              @PathVariable(value = "ingredientId") Long ingredientId) {
        ingredientService.addExistingIngredientByDishId(dishId, ingredientId);
    }

    //Put
    @PutMapping("ingredients/{id}")
    public void updateIngredient(@PathVariable("id") Long ingredientId,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) Float fats,
                                 @RequestParam(required = false) Float carbs,
                                 @RequestParam(required = false) Float proteins,
                                 @RequestParam(required = false) Integer calories,
                                 @RequestParam(required = false) Integer weight) {
        ingredientService.updateIngredient(ingredientId, name, fats, carbs, proteins, calories, weight);
    }
    @PutMapping("ingredient/{id}")
    public Ingredient editDish(@RequestBody Ingredient newIngredient, @PathVariable("id") Long ingredientId) {
        return ingredientService.editIngredient(newIngredient, ingredientId);
    }

    //Delete
    @DeleteMapping("/ingredients/{id}")
    public void deleteIngredient(@PathVariable(value = "id") Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }

    @DeleteMapping("/dishes/{dishId}/ingredients/{ingredientId}")
    public void deleteIngredientFromDish(@PathVariable(value = "dishId") Long dishId,
                                         @PathVariable(value = "ingredientId") Long ingredientId) {
        ingredientService.deleteIngredientFromDish(dishId, ingredientId);
    }
}
