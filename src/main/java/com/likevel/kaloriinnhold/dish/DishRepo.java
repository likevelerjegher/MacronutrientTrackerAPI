package com.likevel.kaloriinnhold.dish;

import org.springframework.http.ResponseEntity;

public interface DishRepo {
    ResponseEntity<Object> getNutritionalData(String ingredient, String weight);
}
