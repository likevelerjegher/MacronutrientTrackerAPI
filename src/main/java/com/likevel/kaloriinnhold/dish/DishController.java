package com.likevel.kaloriinnhold.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/dish")
public class DishController {

    private final DishService dishService;
    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
    @GetMapping("/calculate")
    public ResponseEntity<Object>  getDishesWithParams(@RequestParam(name="name") String name,
                                                       @RequestParam(name="weight") String weight){
        return new ResponseEntity<>(dishService.getNutritionalData(name, weight), HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity<List<Dish>> getDishes(){
        return new ResponseEntity<>(dishService.allDishes(), HttpStatus.OK);
    }
    @GetMapping("/{name}")
    public ResponseEntity<Optional<Dish>> getSingleDish(@PathVariable String name){
        return new ResponseEntity<>(dishService.singleDish(name), HttpStatus.OK);
    }
}
