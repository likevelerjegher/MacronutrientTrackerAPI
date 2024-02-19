package com.likevel.kaloriinnhold.dish;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends MongoRepository<Dish, ObjectId> {
    Optional<Dish> findDishByName(String name);

}
