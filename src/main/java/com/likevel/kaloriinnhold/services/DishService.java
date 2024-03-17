package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
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

    public List<DishEntity> getDishes() {
        return dishRepository.findAll();
    }

    public DishEntity getDishById(Long dishId) {
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish '" + dishId + "' is not found (does not exist)."));
    }

    //Post
    public void createNewDish(DishEntity dish) {
        Optional<DishEntity> dishOptional = dishRepository
                .findDishByDishName(dish.getDishName());
        if (dishOptional.isPresent()) {
            throw new IllegalStateException("dish with this name already exists.");
        }
        dishRepository.save(dish);
    }

    //Put
    @Transactional
    public void updateDish(Long dishId, String dishName,
                           Float servings) {
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish with id " + dishId + "is not updated (does not exist)."));
        if (dishName != null && !dishName.isEmpty() && !Objects.equals(dish.getDishName(), dishName)) {
            Optional<DishEntity> dishOptional = dishRepository.findDishByDishName(dishName);
            if (dishOptional.isPresent()) {
                throw new IllegalStateException("dish with this name already exists.");
            }
            dish.setDishName(dishName);
        }
        if (servings != null && servings > 0) {
            dish.setServings(servings);
        }
    }

    //Delete
    public void deleteDish(Long dishId) {
        boolean exists = dishRepository.existsById(dishId);
        if (!exists) {
            throw new IllegalStateException(
                    "dish id: " + dishId + "is not deleted (does not exist)");
        }
        dishRepository.deleteById(dishId);
    }

    public void deleteDishes() {
        dishRepository.deleteAll();
    }

}
