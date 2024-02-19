package com.likevel.kaloriinnhold.dish;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;
    public List<Dish> allDishes(){
        return dishRepository.findAll();
//        return List.of(new Dish("oil", 0f, 3f, 0f, 27),
//                new Dish("milk", 12f, 3f, 2f, 55),
//                new Dish("steak", 23f, 332f, 3f, 123));
    }

    public Optional<Dish> singleDish(String name){
        return dishRepository.findMovieByName(name);
    }

}
