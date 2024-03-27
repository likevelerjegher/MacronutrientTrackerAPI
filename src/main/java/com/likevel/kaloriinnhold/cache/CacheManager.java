package com.likevel.kaloriinnhold.cache;
import com.likevel.kaloriinnhold.model.Comment;
import com.likevel.kaloriinnhold.model.Dish;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class CacheManager {
    private final ConcurrentMap<Long, Dish> cache = new ConcurrentHashMap<>();

    public void put(Long id, Dish dish) {
        cache.put(id, dish);
    }

    public Dish get(Long id) {
        return cache.get(id);
    }

    public boolean contains(Long id) {
        return cache.containsKey(id);
    }

    public void remove(Long id) {
        cache.remove(id);
    }
    public void clear(){
        cache.clear();
    }
    public List<Dish> getAllDishes() {
        return new ArrayList<>(cache.values());
    }

    public void putAllDishes(List<Dish> dishes) {
        dishes.forEach(dish -> cache.put(dish.getId(), dish));
    }

}