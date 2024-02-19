package com.likevel.kaloriinnhold.dish;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/dish")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
    @GetMapping("/calculate")
    public String getDishesWithParams(@RequestParam String name, @RequestParam Integer calorie){
        return "name is " + name + "; calorie content: " + calorie;
    }
    @GetMapping
    public ResponseEntity<List<Dish>> getDishes(){
        return new ResponseEntity<List<Dish>>(dishService.allDishes(), HttpStatus.OK);
    }
//    @GetMapping("/{name}")
//    public ResponseEntity<Optional<Dish>> getSingleDish(@PathVariable String name){
//        return new ResponseEntity<Optional<Dish>>(dishService.singleDish(name), HttpStatus.OK);
//    }
}
