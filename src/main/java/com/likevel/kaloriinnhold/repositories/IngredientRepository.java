package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Ingredient findIngredientByName(String name);

    List<Ingredient> findAll();
}
