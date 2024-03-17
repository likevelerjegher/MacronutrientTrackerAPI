package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.entity.DishEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<DishEntity, Long> {
    Optional<DishEntity> findDishByDishName(String name);

    List<DishEntity> findAll();
}
