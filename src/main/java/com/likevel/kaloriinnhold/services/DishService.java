package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.cache.CacheManager;
import com.likevel.kaloriinnhold.exception.ObjectExistedException;
import com.likevel.kaloriinnhold.exception.ObjectNotFoundException;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final CacheManager<String, Object> cache;
    private static final String DISH = "dish ";

    @Autowired
    public DishService(DishRepository dishRepository, CacheManager<String, Object> cache) {
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
        return dishRepository.findAll();
    }

    public Dish getDishById(Long dishId) {
        Object cachedData = cache.get(DISH + dishId.toString());
        if (cachedData!=null) {
            return (Dish) cachedData;
        } else {
            Dish dish = dishRepository.findById(dishId).orElse(null);
            if (dish != null) {
                cache.put(DISH + dishId, dish);
            }
            return dish;
        }
    }

    public List<Dish> getDishesWithLessOrSameCalories(Integer calories) {
        return dishRepository.getDishesWithLessOrSameCalories(calories);
    }

    //Post
    public void createNewDish(Dish dish) {
        Optional<Dish> dishOptional = dishRepository
                .findDishByDishName(dish.getDishName());
        if (dishOptional.isPresent()) {
            throw new ObjectExistedException("dish with this name already exists.");
        }
        Dish newDish = dishRepository.save(dish);
        cache.put(DISH + dish.getId().toString(), newDish);
    }

    //Put
    @Transactional
    public void updateDish(Long dishId, String dishName,
                           Float servings) {

        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "dish with id " + dishId + "is not updated (does not exist)."));
        if (dishName != null && !dishName.isEmpty() && !Objects.equals(dish.getDishName(), dishName)) {
            Optional<Dish> dishOptional = dishRepository.findDishByDishName(dishName);
            if (dishOptional.isPresent()) {
                throw new ObjectExistedException("dish with this name already exists.");
            }
            dish.setDishName(dishName);
        }
        if (servings != null && servings > 0) {
            dish.setServings(servings);
        }
        cache.put(DISH + dishId, dish);
    }

    //Delete
    public void deleteDish(Long dishId) {
        boolean exists = dishRepository.existsById(dishId);
        if (!exists) {
            throw new ObjectNotFoundException(
                    "dish id: " + dishId + "is not deleted (does not exist)");
        }
        dishRepository.deleteById(dishId);
        cache.remove(DISH + dishId);
    }

    public void deleteDishes() {
        dishRepository.deleteAll();
        cache.clear();
    }
}
