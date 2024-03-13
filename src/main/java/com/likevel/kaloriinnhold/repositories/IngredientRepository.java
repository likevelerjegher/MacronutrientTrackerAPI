package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.entity.IngredientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<IngredientEntity, Long> {
    IngredientEntity findIngredientsByName(String name);
    List<IngredientEntity> findAll();
}
