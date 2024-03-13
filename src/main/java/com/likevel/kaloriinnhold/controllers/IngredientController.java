package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.entity.IngredientEntity;
import com.likevel.kaloriinnhold.exception.IngredientNotFoundException;
import com.likevel.kaloriinnhold.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IngredientController {
    private IngredientService ingredientService;
    @Autowired
    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }
//Get
    @GetMapping("dishes/{dishId}/ingredients")
    public List<IngredientEntity> getIngredientsByDishId(@PathVariable(value = "dishId") Long dishId){
        return ingredientService.getIngredientsByDishId(dishId);
    }
    @GetMapping("ingredients/{ingredientId}/dishes")
    public List<DishEntity>getDishesByIngredientId(@PathVariable(value = "ingredientId") Long ingredientId){
        return ingredientService.getDishesByIngredientId(ingredientId);
    }
    @GetMapping("/ingredients")
    public List<IngredientEntity> getIngredients(){
        return ingredientService.getIngredients();
    }
//Post
    @PostMapping("dishes/{dishId}/ingredients")
    public void addNewIngredientByDishId(@PathVariable(value = "dishId") Long dishId,
                                         @RequestBody IngredientEntity ingredient){
        ingredientService.addNewIngredientByDishId(dishId, ingredient);
    }

//Put
    @PutMapping("ingredients/{id]")
    public void updateIngredient(@PathVariable("id") Long ingredientId,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) Float fats,
                                 @RequestParam(required = false) Float carbs,
                                 @RequestParam(required = false) Float proteins,
                                 @RequestParam(required = false) Integer calories,
                                 @RequestParam(required = false) Integer weight){
        ingredientService.updateIngredient(ingredientId, name, fats, carbs, proteins, calories, weight);
    }

//Delete
    @DeleteMapping("/ingredients/{id}")
    public void deleteIngredient(@PathVariable(value = "id") Long ingredientId){
        ingredientService.deleteIngredient(ingredientId);
    }
    @DeleteMapping("/dishes/{dishId}/ingredients/{ingredientId}")
    public void deleteIngredientFromDish(@PathVariable(value = "dishId") Long dishId,
                                         @PathVariable(value = "ingredientId") Long ingredientId){
        ingredientService.deleteIngredientFromDish(dishId, ingredientId);
    }
}
