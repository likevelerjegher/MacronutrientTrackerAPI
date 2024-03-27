package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.cache.CacheManager;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.model.Ingredient;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.repositories.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private IngredientRepository ingredientRepository;
    private DishRepository dishRepository;
    static final Logger LOGGER = LogManager.getLogger(DishService.class);

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository,
                             DishRepository dishRepository) {
        this.ingredientRepository = ingredientRepository;
        this.dishRepository = dishRepository;
    }

    //Get
    public List<Ingredient> getIngredientsByDishId(Long dishId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish '" + dishId + "' does not exist, can't view its ingredients."));
        LOGGER.info("retrieving ingredients from DB by dish id.");
        return dish.getIngredients();
    }

    public List<Dish> getDishesByIngredientId(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ingredient with id " + ingredientId + " does not exist, can't view its dishes."));
        LOGGER.info("retrieving dishes from DB by ingredient id.");
        return ingredient.getDishes();
    }

    public List<Ingredient> getIngredients() {
        LOGGER.info("retrieving ingredients from DB.");
        return ingredientRepository.findAll();
    }

    //Post
    public void addNewIngredientByDishId(Long dishId, Ingredient ingredientRequest) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish id: " + dishId + " does not exist, can't add ingredient to it."));
        Ingredient ingredient = ingredientRepository.findIngredientByName(ingredientRequest.getName());
        if (dish.getIngredients().stream().noneMatch(ingredientFunc -> ingredientFunc.getName().equals(ingredientRequest.getName()))) {
            if (ingredient != null) {
                ingredientRepository.save(ingredient);
                dish.getIngredients().add(ingredient);
                addNutritionToDish(dish, ingredient);
            } else {
                ingredientRepository.save(ingredientRequest);
                dish.getIngredients().add(ingredientRequest);
                addNutritionToDish(dish, ingredientRequest);
            }
        } else {
            LOGGER.info("New ingredient was not added to the dish.");
            throw new EntityNotFoundException("ingredient with name " + ingredientRequest.getName()
                    + " already exists in the dish " + dish.getDishName() + ".");
        }
        LOGGER.info("New ingredient was added to the dish.");
    }

    public void addExistingIngredientByDishId(Long dishId, Long ingredientId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish id: " + dishId + " does not exist, can't add ingredient to it."));
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ingredient id: " + ingredientId + " does not exist, therefore cannot delete it"));
        dish.getIngredients().add(ingredient);
        LOGGER.info("The ingredient was added to the dish.");
        addNutritionToDish(dish, ingredient);
        LOGGER.info("Nutritional data of the dish has been updated.");
    }

    //Put
    @Transactional
    public void updateIngredient(Long ingredientId, String ingredientName,
                                 Float ingredientFats,
                                 Float ingredientCarbs,
                                 Float ingredientProteins,
                                 Integer ingredientCalories,
                                 Integer ingredientWeight) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ingredient id: " + ingredientId + " does not exist, therefore can't update it."));
        if (ingredientName != null && !ingredientName.isEmpty() && !Objects.equals(ingredient.getName(), ingredientName)) {
            Optional<Ingredient> ingredientOptional = Optional.ofNullable(ingredientRepository.findIngredientByName(ingredientName));
            if (ingredientOptional.isPresent()) {
                throw new EntityNotFoundException("ingredient with this name already exists.");
            }
            ingredient.setName(ingredientName);
        }
        if (ingredientFats != null && ingredientFats >= 0) ingredient.setFats(ingredientFats);
        if (ingredientCarbs != null && ingredientCarbs >= 0) ingredient.setCarbs(ingredientCarbs);
        if (ingredientProteins != null && ingredientProteins >= 0) ingredient.setProteins(ingredientProteins);
        if (ingredientCalories != null && ingredientCalories >= 0) ingredient.setCalories(ingredientCalories);
        if (ingredientWeight != null && ingredientWeight >= 0) ingredient.setWeight(ingredientWeight);
        LOGGER.info("The ingredient has been updated");
    }

    //Delete
    @Transactional
    public void deleteIngredient(Long ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ingredient with id " + ingredientId + " does not exist, therefore can't delete it."));
        List<Dish> dishes = ingredient.getDishes();
        for (Dish dish : dishes) {
            subtractNutritionInDish(dish, ingredient);
            dish.getIngredients().remove(ingredient);
            dishRepository.save(dish);
        }
        ingredientRepository.delete(ingredient);
        LOGGER.info("The ingredient has been deleted from DB.");
    }

    @Transactional
    public void deleteIngredientFromDish(Long dishId, Long ingredientId) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish " + ingredientId + " does not exist, therefore cannot delete it."));
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new IllegalStateException(
                        "ingredient " + ingredientId + " does not exist, therefore cannot delete it"));

        subtractNutritionInDish(dish, ingredient);
        dish.getIngredients().remove(ingredient);
        LOGGER.info("The ingredient has been updated from the dish.");
    }

    public void addNutritionToDish(Dish dish, Ingredient ingredient) {
        if (ingredient.getFats() != null && ingredient.getFats() >= 0) {
            dish.setDishFats(dish.getDishFats() + ingredient.getFats());
        }
        if (ingredient.getCarbs() != null && ingredient.getCarbs() >= 0) {
            dish.setDishCarbs(dish.getDishCarbs() + ingredient.getCarbs());
        }
        if (ingredient.getProteins() != null && ingredient.getProteins() >= 0) {
            dish.setDishProteins(dish.getDishProteins() + ingredient.getProteins());
        }
        if (ingredient.getCalories() != null && ingredient.getCalories() >= 0) {
            dish.setDishCalories(dish.getDishCalories() + ingredient.getCalories());
        }
        dishRepository.save(dish);
    }

    public void subtractNutritionInDish(Dish dish, Ingredient ingredient) {
        if (ingredient.getFats() != null && ingredient.getFats() >= 0) {
            dish.setDishFats(dish.getDishFats() - ingredient.getFats());
        }
        if (ingredient.getCarbs() != null && ingredient.getCarbs() >= 0) {
            dish.setDishCarbs(dish.getDishCarbs() - ingredient.getCarbs());
        }
        if (ingredient.getProteins() != null && ingredient.getProteins() >= 0) {
            dish.setDishProteins(dish.getDishProteins() - ingredient.getProteins());
        }
        if (ingredient.getCalories() != null && ingredient.getCalories() >= 0) {
            dish.setDishCalories(dish.getDishCalories() - ingredient.getCalories());
        }
        dishRepository.save(dish);
    }
}
