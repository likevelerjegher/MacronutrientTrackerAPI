package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.entity.DishEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends CrudRepository<DishEntity, Long> {
    Optional<DishEntity> findDishByName(String name);

    List<DishEntity> findAll();
}


//7 - one-to-many
//open_session_in_view
//mysql
//10 - many-to-many