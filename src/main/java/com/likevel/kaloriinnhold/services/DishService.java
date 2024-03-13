package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.entity.IngredientEntity;
import com.likevel.kaloriinnhold.exception.DishAlreadyExistException;
import com.likevel.kaloriinnhold.exception.DishNotFoundException;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DishService{
    @Autowired
    private final DishRepository dishRepository;
    @Autowired
    private final IngredientRepository ingredientRepository;

    @Value("${edamam.api.appId}")
    private String appId;
    @Value("${edamam.api.appKey}")
    private String appKey;

    public ResponseEntity<Object> getNutritionalData(String name, Long weight) {
        String apiUrl = "https://api.edamam.com/api/nutrition-data";
        String url = "%s?app_id=%s&app_key=%s&nutrition-type=cooking&ingr=%s%%%dg".formatted(apiUrl, appId, appKey, name, weight);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return ResponseEntity.ok(restTemplate.getForObject(url, Object.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid dish. Please check the spelling of the ingredient and try again.");
        }
    }

    public Dish newDish(DishEntity dish) throws DishAlreadyExistException {
        if (dishRepository.findByName(dish.getName()) != null){
            throw new DishAlreadyExistException("Dish with this name already exists.");
        }
        return Dish.toModel(dishRepository.save(dish));
    }
//    public Dish addIngredientToDish(Long dishId, Long ingredientId) throws DishNotFoundException {
//        DishEntity dish = dishRepository.findById(dishId).get();
//        if (dish == null){
//            throw new DishNotFoundException("Dish is not found.");
//        }
//        IngredientEntity ingredient = ingredientRepository.findById(ingredientId).get();
//        dish.setIngredients(ingredient);
//        return Dish.toModel(dishRepository.save(dish));
//
//    }
    public Dish getDishById(Long id) throws DishNotFoundException {
        DishEntity dish = dishRepository.findById(id).get();
        if (dish == null){
            throw new DishNotFoundException("Dish is not found.");
        }
        return Dish.toModel(dish);
    }
    public Long deleteDish(Long id){
        dishRepository.deleteById(id);
        return id;
    }

}
