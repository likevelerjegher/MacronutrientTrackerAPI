package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.entity.IngredientEntity;
import com.likevel.kaloriinnhold.exception.IngredientAlreadyExistException;
import com.likevel.kaloriinnhold.exception.IngredientNotFoundException;

import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.repositories.IngredientRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public void addNewIngredientByDishId(Long dishId, IngredientEntity ingredientRequest){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalIdentifierException(
                        "dish with id " + dishId + " does not exist, can't add ingredient to it."));
        IngredientEntity ingredient = ingredientRepository. findIngredientsByName(ingredientRequest.getName());
        if(dish.getIngredients().stream().noneMatch(ingredientFunc -> ingredientFunc.getName().equals(ingredientRequest.getName()))){
            if (ingredient != null){
                ingredientRepository.save(ingredient);
                dish.getIngredients().add(ingredient);
                dishRepository.save(dish);
            }else{
                ingredientRepository.save(ingredientRequest);
                dish.getIngredients().add(ingredientRequest);
                dishRepository.save(dish);
            }
        }else{
            throw new IllegalStateException("ingredient with name " + ingredientRequest.getName()
                    + " already exists in the dish " + dish.getName() + ".");
        }
    }
//Put
    @Transactional
    public void updateIngredient(Long ingredientId, String name,
                                 Float fats, Float carbs, Float proteins,
                                 Integer calories, Integer weight){
        IngredientEntity ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalStateException(
                        "ingredient with id " + ingredientId + " does not exist, therefore can't update it."));
        if(name != null && !name.isEmpty() && !Objects.equals(ingredient.getName(),name)){
            Optional<IngredientEntity> ingredientOptional = Optional.ofNullable(ingredientRepository.findIngredientsByName(name));
            if(ingredientOptional.isPresent()){
                throw new IllegalStateException("ingredient with this name already exists.");
            }
            ingredient.setName(name);
        }
        if (fats != null && fats > 0) ingredient.setFats(fats);
        if (carbs != null && carbs > 0) ingredient.setCarbs(carbs);
        if (proteins != null && proteins > 0) ingredient.setProteins(proteins);
        if (calories != null && calories > 0) ingredient.setCalories(calories);
        if (weight != null && weight > 0) ingredient.setWeight(weight);
    }
//Delete
    public void deleteIngredient(Long ingredientId){
        IngredientEntity ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalStateException(
                        "ingredient with id " + ingredientId + " does not exist, therefore can't delete it."));
        List<DishEntity> dishes = ingredient.getDishes();
        for(DishEntity dish: dishes){
            dish.getIngredients().remove(ingredient);
            dishRepository.save(dish);
        }
        ingredientRepository.delete(ingredient);
    }
    public void deleteIngredientFromDish(Long dishId, Long ingredientId){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish with id " + ingredientId + " does not exist, therefore cannot delete it."));
        IngredientEntity ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalStateException(
                        "ingredient with id " + ingredientId + " does not exist, therefore cannot delete it"));
        dish.getIngredients().remove(ingredient);
        dishRepository.save(dish);
    }

}
