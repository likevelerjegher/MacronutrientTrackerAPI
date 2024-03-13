package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.entity.IngredientEntity;
import com.likevel.kaloriinnhold.exception.IngredientAlreadyExistException;
import com.likevel.kaloriinnhold.exception.IngredientNotFoundException;
import com.likevel.kaloriinnhold.model.Ingredient;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private DishRepository dishRepository;
//Get
    public List<IngredientEntity> getIngredientsByDishId(Long dishId){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish with id " + dishId + " does not exist, can't view its ingredients."));
        return dish.getIngredients();
    }
    public List<DishEntity> getDishesByIngredientId(Long ingredientId){
        IngredientEntity ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalStateException(
                        "ingredient with id " + ingredientId + " does not exist, can't view its dishes."));
        return ingredient.getDishes();
    }
    public List<IngredientEntity> getIngredients(){
        return ingredientRepository.findAll();
    }
//Post
    public Ingredient createIngredient(IngredientEntity ingredient, Long dishId) throws IngredientAlreadyExistException {
        if (ingredientRepository.findByName(ingredient.getName()) != null){
            throw new IngredientAlreadyExistException("Ingredient with this name already exists.");
        }
        //DishEntity dish = dishRepository.findById(dishId).get();
        List<DishEntity> currentDishes = ingredient.getDish();
        ingredient.setDish(currentDishes);
        return Ingredient.toModel(ingredientRepository.save(ingredient));
    }
//Put
    public Ingredient updateIngredientNutritionalData(Long id){
        IngredientEntity ingredient = ingredientRepository.findById(id).get();

        return Ingredient.toModel(ingredientRepository.save(ingredient));
    }
//Delete
    public Long deleteIngredient(Long id){
        ingredientRepository.deleteById(id);
        return id;
    }

//    public Long deleteDish(Long id){
//        dishRepository.deleteById(id);
//        return id;
//    }
}
