package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.entity.IngredientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<IngredientEntity, Long> {
    IngredientEntity findIngredientByName(String name);

    List<IngredientEntity> findAll();
}
