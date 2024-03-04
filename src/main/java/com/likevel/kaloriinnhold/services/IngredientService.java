package com.likevel.kaloriinnhold.services;

import com.likevel.kaloriinnhold.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

}
