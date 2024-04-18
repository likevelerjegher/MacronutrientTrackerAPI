package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.cache.CacheManager;
import com.likevel.kaloriinnhold.exception.ObjectExistedException;
import com.likevel.kaloriinnhold.exception.ObjectNotFoundException;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    private static final Logger logger = LoggerFactory.getLogger(DishService.class);


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
    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public Dish getDishById(Long dishId) {
        Object cachedData = cache.get(DISH + dishId.toString());
        if (cachedData != null) {
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

    public List<Dish> saveDishes(final List<Dish> dishes) {
        List<Dish> existingDishes = dishes.stream()
                .filter(dish -> dishRepository.existsByName(dish.getDishName()))
                .toList();

        List<Dish> createdDishes = dishes.stream()
                .filter(dish -> !dishRepository.existsByName(dish.getDishName()))
                .map(dishRepository::save)
                .toList();
        existingDishes.forEach(dish -> logger.warn("City with name '{}' already exists", dish.getDishName()));
        return createdDishes;
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
