package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.entity.IngredientEntity;
import com.likevel.kaloriinnhold.exception.IngredientNotFoundException;
import com.likevel.kaloriinnhold.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<Object> getIngredientById(@RequestParam(name="id") Long id){
        try {
            return ResponseEntity.ok(ingredientService.getIngredientById(id));
        }catch (IngredientNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing went wrong.");
        }
    }
    @PostMapping
    public ResponseEntity createIngredient(@RequestBody IngredientEntity ingredient,
                                           @RequestParam Long dishId){
        try {
            return ResponseEntity.ok(ingredientService.createIngredient(ingredient, dishId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing went wrong.");
        }

    }
    @PutMapping
    public ResponseEntity updateIngredientNutritionalData(@RequestParam Long id){
        try {
            return ResponseEntity.ok(ingredientService.updateIngredientNutritionalData(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing went wrong.");

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteIngredientFromDish(@PathVariable Long id){
        try {
            return ResponseEntity.ok(ingredientService.deleteIngredient(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing went wrong.");
        }
    }
}
