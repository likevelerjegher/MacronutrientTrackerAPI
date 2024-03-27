package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.cache.CacheManager;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

public class DishService {

    private final DishRepository dishRepository;
    private final CacheManager cache;
    static final Logger LOGGER = LogManager.getLogger(DishService.class);

    @Autowired
    public DishService(DishRepository dishRepository, CacheManager cache) {
        this.dishRepository = dishRepository;
        this.cache = cache;
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
        if (!cache.getAllDishes().isEmpty()) {
            LOGGER.info("Retrieving all the dishes from cache.");
            return cache.getAllDishes();
        } else {
            List<Dish> dishes = dishRepository.findAll();
            LOGGER.info("Adding dishes to cache and retrieving them from DB.");
            cache.putAllDishes(dishes);
            return dishes;
        }
    }

    public Dish getDishById(Long dishId) {
        if (cache.contains(dishId)) {
            LOGGER.info("Retrieving the dish from cache by id.");
            return cache.get(dishId);
        } else {
            Dish dish = dishRepository.findById(dishId).orElse(null);
            if (dish != null) {
                cache.put(dishId, dish);
                LOGGER.info("Adding dish to cache and retrieving it from DB.");
            }
            return dish;
        }
    }

    public List<Dish> getDishesWithLessOrSameCalories(Integer calories) {
        LOGGER.info("Displaying dishes with limited amount of calories.");
        return dishRepository.getDishesWithLessOrSameCalories(calories);
    }

    //Post
    public void createNewDish(Dish dish) {
        Optional<Dish> dishOptional = dishRepository
                .findDishByDishName(dish.getDishName());
        if (dishOptional.isPresent()) {
            LOGGER.info("The dish is not created.");
            throw new IllegalStateException("dish with this name already exists.");
        }
        Dish newDish = dishRepository.save(dish);
        cache.put(newDish.getId(), newDish);
        LOGGER.info("The dish is created.");
    }

    //Put
    @Transactional
    public void updateDish(Long dishId, String dishName,
                           Float servings) {

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "dish with id " + dishId + "is not updated (does not exist)."));
        if (dishName != null && !dishName.isEmpty() && !Objects.equals(dish.getDishName(), dishName)) {
            Optional<Dish> dishOptional = dishRepository.findDishByDishName(dishName);
            if (dishOptional.isPresent()) {
                throw new EntityNotFoundException("dish with this name already exists.");
            }
            dish.setDishName(dishName);
            LOGGER.info("Dish name is updated.");

        }
        if (servings != null && servings > 0) {
            dish.setServings(servings);
            LOGGER.info("Dish servings are updated.");

        }
        cache.put(dishId, dish);
        LOGGER.info("The dish is updated.");
    }

    //Delete
    public void deleteDish(Long dishId) {
        boolean exists = dishRepository.existsById(dishId);
        if (!exists) {
            throw new EntityNotFoundException(
                    "dish id: " + dishId + "is not deleted (does not exist)");
        }
        dishRepository.deleteById(dishId);
        cache.remove(dishId);
        LOGGER.info("The dish is deleted.");

    }

    public void deleteDishes() {
        dishRepository.deleteAll();
        cache.clear();
        LOGGER.info("All the dishes are deleted.");

    }

}
