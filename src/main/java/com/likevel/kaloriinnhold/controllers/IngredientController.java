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
    @PostMapping
    public ResponseEntity createIngredient(@RequestBody IngredientEntity ingredient,
                                           @RequestParam Long dishId){
        try {
            return ResponseEntity.ok(ingredientService.createIngredient(ingredient, dishId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing went wrong.");
        }

    }
//Put
    @PutMapping
    public ResponseEntity updateIngredientNutritionalData(@RequestParam Long id){
        try {
            return ResponseEntity.ok(ingredientService.updateIngredientNutritionalData(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing went wrong.");

        }
    }
//Delete
    @DeleteMapping("/{id}")
    public ResponseEntity deleteIngredientFromDish(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ingredientService.deleteIngredient(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing went wrong.");
        }
    }
}
