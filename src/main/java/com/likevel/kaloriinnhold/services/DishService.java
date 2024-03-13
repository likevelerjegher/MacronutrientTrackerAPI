package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.repositories.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DishService{
    @Autowired
    private final DishRepository dishRepository;

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
    public List<DishEntity> getDishes(){
        return dishRepository.findAll();
    }
    public DishEntity getDishById(Long dishId){
        return dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish with id " + dishId + "is not found (does not exist)."));
    }
//Post
    public void createNewDish(DishEntity dish){
        Optional<DishEntity> dishOptional = dishRepository
                .findDishByName(dish.getName());
        if(dishOptional.isPresent()){
            throw new IllegalStateException("dish with this name already exists.");
        }
        dishRepository.save(dish);
    }
//Put
    @Transactional
    public void updateDish(Long dishId, String name,
                           Float fats,
                           Float carbs,
                           Float proteins,
                           Integer calories, Float servings){
        DishEntity dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalStateException(
                        "dish with id " + dishId + "is not updated (does not exist)."));
        if(name != null && !name.isEmpty() && !Objects.equals(dish.getName(), name)){
            Optional<DishEntity> dishOptional = dishRepository.findDishByName(name);
            if (dishOptional.isPresent()){
                throw new IllegalStateException("dish with this name already exists.");
            }
            dish.setName(name);
        }

        if (fats != null && fats > 0){
            dish.setFats(fats);
        }
        if (carbs != null && carbs > 0){
            dish.setCarbs(carbs);
        }
        if (proteins != null && proteins > 0){
            dish.setProteins(proteins);
        }
        if (calories != null && calories > 0){
            dish.setCalories(calories);
        }
        if (servings != null && servings > 0){
            dish.setServings(servings);
        }
    }
//Delete
    public void deleteDish(Long dishId){
        boolean exists = dishRepository.existsById(dishId);
        if (!exists){
            throw new IllegalStateException(
                    "dish with id " + dishId + "is not deleted (does not exist)");
        }
        dishRepository.deleteById(dishId);
    }
    public void deleteDishes(){
        dishRepository.deleteAll();
    }

}
