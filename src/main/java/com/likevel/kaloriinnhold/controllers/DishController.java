package com.likevel.kaloriinnhold.controllers;

import com.likevel.kaloriinnhold.entity.DishEntity;
import com.likevel.kaloriinnhold.exception.DishAlreadyExistException;
import com.likevel.kaloriinnhold.exception.DishNotFoundException;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.services.DishService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
@AllArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping("/calculate")
    public ResponseEntity<Object> getDishesWithParams(@RequestParam(name="name") String name,
                                                       @RequestParam(name="weight") Long weight){
        return new ResponseEntity<>(dishService.getNutritionalData(name, weight), HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity<Object> getDishById(@RequestParam(name="id") Long id){
        try {
            return ResponseEntity.ok(dishService.getDishByName(id));
        }catch (DishNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing's wrong");
        }

    }
    @PostMapping("/new")
    public ResponseEntity newDish(@RequestBody DishEntity dish){
        try {
            dishService.newDish(dish);
            return ResponseEntity.ok("everything's fine, saved");
        }catch (DishAlreadyExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("smthing's wrong");
        }
    }
}