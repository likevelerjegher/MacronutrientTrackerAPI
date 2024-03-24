package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.cache.CacheManager;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DishService {
    private static final String ALL_DISHES_REQUEST = "http://localhost:8080/api/dishes";
    private static final String DISH_BY_ID_REQUEST = "http://localhost:8080/api/";
    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Value("${edamam.api.appId}")
    private String appId;
    @Value("${edamam.api.appKey}")
    private String appKey;

    //Get
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

    public List<Dish> getDishes() {
        if(CacheManager.containsKey(ALL_DISHES_REQUEST)){
            return (List<Dish>) CacheManager.get(ALL_DISHES_REQUEST);
        }else{
            List<Dish> dishes = dishRepository.findAll();
            CacheManager.put(ALL_DISHES_REQUEST, dishes);
            return dishes;
        }
    }

    public Dish getDishById(Long dishId) {
        if (CacheManager.containsKey(DISH_BY_ID_REQUEST + dishId.toString())){
            return ((List<Dish>) CacheManager.get(DISH_BY_ID_REQUEST+dishId)).get(0);
        }else {
            Dish dish = dishRepository.findById(dishId).orElse(null);
            List<Dish> dishes = new ArrayList<>();
            dishes.add(dish);
            CacheManager.put(DISH_BY_ID_REQUEST + dishId, dishes);
            return dish;
        }
    }
    public List<Dish> getDishesWithLessOrSameCalories(Integer caloriesLimit){
        return dishRepository.getDishesWithLessOrSameCalories(caloriesLimit);
    }

    //Post
    public void createNewDish(Dish dish) {
        Optional<Dish> dishOptional = dishRepository
                .findDishByDishName(dish.getDishName());
        if (dishOptional.isPresent()) {
            throw new IllegalStateException("dish with this name already exists.");
        }
        dishRepository.save(dish);
        CacheManager.clear();

    }

    //Put
    @Transactional
    public void updateDish(Long dishId, String dishName,
                           Float servings) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish with id " + dishId + "is not updated (does not exist)."));
        if (dishName != null && !dishName.isEmpty() && !Objects.equals(dish.getDishName(), dishName)) {
            Optional<Dish> dishOptional = dishRepository.findDishByDishName(dishName);
            if (dishOptional.isPresent()) {
                throw new IllegalStateException("dish with this name already exists.");
            }
            dish.setDishName(dishName);
        }
        if (servings != null && servings > 0) {
            dish.setServings(servings);
        }
        CacheManager.clear();
    }

    //Delete
    public void deleteDish(Long dishId) {
        boolean exists = dishRepository.existsById(dishId);
        if (!exists) {
            throw new IllegalStateException(
                    "dish id: " + dishId + "is not deleted (does not exist)");
        }
        dishRepository.deleteById(dishId);
        CacheManager.clear();

    }

    public void deleteDishes() {
        dishRepository.deleteAll();
        CacheManager.clear();
    }

}
