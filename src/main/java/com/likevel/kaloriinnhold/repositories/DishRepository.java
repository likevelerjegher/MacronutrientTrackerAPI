package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<Dish, Long> {
    @Query("SELECT a FROM Dish a WHERE a.dishCalories <= :caloriesLimit ORDER BY a.dishCalories DESC")
    List<Dish> getDishesWithLessOrSameCalories(@Param("caloriesLimit")Integer caloriesLimit);
    boolean existsByDishName(String dishName);
    Optional<Dish> findDishByDishName(String dishName);
    List<Dish> findAll();
}
