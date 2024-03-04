package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.entity.IngredientEntity;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<IngredientEntity, Long> {
    IngredientEntity findByName(String name);
}
