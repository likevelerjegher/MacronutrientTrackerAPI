package com.likevel.kaloriinnhold.repositories;

import com.likevel.kaloriinnhold.entity.DishEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends CrudRepository<DishEntity, Long> {
    DishEntity findByName(String name);
}


//7 - one-to-many
//open_session_in_view
//mysql
//10 - many-to-many