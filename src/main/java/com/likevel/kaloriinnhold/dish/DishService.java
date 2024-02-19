package com.likevel.kaloriinnhold.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class DishService implements DishRepo{

    private DishRepository dishRepository;

    @Value("${edamam.api.appId}")
    private String appId;
    @Value("${edamam.api.appKey}")
    private String appKey;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public ResponseEntity<Object> getNutritionalData(String name, String weight) {
        String apiUrl = "https://api.edamam.com/api/nutrition-data";
        String url = "%s?app_id=%s&app_key=%s&nutrition-type=cooking&ingr=%s%%%sg".formatted(apiUrl, appId, appKey, name, weight);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return ResponseEntity.ok(restTemplate.getForObject(url, Object.class));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid category. Please use one of the following categories: business, entertainment, general, health, science, sports, technology.");
        }    }

    public List<Dish> allDishes(){
        return dishRepository.findAll();
    }

    public Optional<Dish> singleDish(String name){
        return dishRepository.findDishByName(name);
    }
}
