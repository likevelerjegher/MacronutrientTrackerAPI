package com.likevel.kaloriinnhold.service;

import com.likevel.kaloriinnhold.exception.ObjectExistedException;
import com.likevel.kaloriinnhold.exception.ObjectNotFoundException;
import com.likevel.kaloriinnhold.model.Dish;
import com.likevel.kaloriinnhold.model.Ingredient;
import com.likevel.kaloriinnhold.repositories.DishRepository;
import com.likevel.kaloriinnhold.repositories.IngredientRepository;
import com.likevel.kaloriinnhold.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIngredientsByDishId() {
        // Arrange
        Long dishId = 1L;
        Dish dish = new Dish();
        dish.setId(dishId);
        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(new Ingredient());
        dish.setIngredients(expectedIngredients);

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));

        // Act
        List<Ingredient> actualIngredients = ingredientService.getIngredientsByDishId(dishId);

        // Assert
        assertEquals(expectedIngredients, actualIngredients);
    }

    @Test
    void testGetIngredientsByDishId_ThrowsObjectNotFoundException() {
        // Arrange
        Long dishId = 1L;

        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> {
            ingredientService.getIngredientsByDishId(dishId);
        });
    }

    @Test
    void testGetDishesByIngredientId() {
        // Arrange
        Long ingredientId = 1L;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        List<Dish> expectedDishes = new ArrayList<>();
        expectedDishes.add(new Dish());
        ingredient.setDishes(expectedDishes);

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Act
        List<Dish> actualDishes = ingredientService.getDishesByIngredientId(ingredientId);

        // Assert
        assertEquals(expectedDishes, actualDishes);
    }

    @Test
    void testGetDishesByIngredientId_ThrowsObjectNotFoundException() {
        // Arrange
        Long ingredientId = 1L;

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> {
            ingredientService.getDishesByIngredientId(ingredientId);
        });
    }

    @Test
    void testGetIngredients() {
        // Arrange
        List<Ingredient> expectedIngredients = new ArrayList<>();
        expectedIngredients.add(new Ingredient());

        when(ingredientRepository.findAll()).thenReturn(expectedIngredients);

        // Act
        List<Ingredient> actualIngredients = ingredientService.getIngredients();

        // Assert
        assertEquals(expectedIngredients, actualIngredients);
    }

    @Test
    void testAddNewIngredientByDishId_IngredientExistsInDish() {
        // Arrange
        Long dishId = 1L;
        Long existingIngredientId = 2L;
        String existingIngredientName = "Existing Ingredient";
        String newIngredientName = "New Ingredient";

        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setIngredients(new ArrayList<>());  // Initialize the ingredients list

        Ingredient existingIngredient = new Ingredient();
        existingIngredient.setId(existingIngredientId);
        existingIngredient.setName(existingIngredientName);
        dish.getIngredients().add(existingIngredient);

        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(newIngredientName);

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findIngredientByName(newIngredientName)).thenReturn(null);

        // Act
        ingredientService.addNewIngredientByDishId(dishId, newIngredient);

        // Assert
        assertTrue(dish.getIngredients().contains(newIngredient));
        verify(ingredientRepository, times(1)).save(newIngredient);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void testAddNewIngredientByDishId_ExistingIngredientIsNull() {
        // Arrange
        Long dishId = 1L;
        Long ingredientId = 2L;
        String newIngredientName = "New Ingredient";

        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setIngredients(new ArrayList<>());  // Initialize the ingredients list

        Ingredient newIngredient = new Ingredient();
        newIngredient.setId(ingredientId);
        newIngredient.setName(newIngredientName);

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findIngredientByName(newIngredientName)).thenReturn(null);

        // Act
        ingredientService.addNewIngredientByDishId(dishId, newIngredient);

        // Assert
        assertTrue(dish.getIngredients().contains(newIngredient));
        verify(ingredientRepository, times(1)).save(newIngredient);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void testAddNewIngredientByDishId_ExistingIngredientExists() {
        // Arrange
        Long dishId = 1L;
        Long existingIngredientId = 2L;
        String existingIngredientName = "Existing Ingredient";
        String newIngredientName = "New Ingredient";

        Dish dish = new Dish();
        dish.setId(dishId);
        dish.setIngredients(new ArrayList<>());  // Initialize the ingredients list

        Ingredient existingIngredient = new Ingredient();
        existingIngredient.setId(existingIngredientId);
        existingIngredient.setName(existingIngredientName);
        dish.getIngredients().add(existingIngredient);

        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(newIngredientName);

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findIngredientByName(newIngredientName)).thenReturn(null);

        // Act
        ingredientService.addNewIngredientByDishId(dishId, newIngredient);

        // Assert
        assertTrue(dish.getIngredients().contains(newIngredient));
        verify(ingredientRepository, times(1)).save(newIngredient);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void testDeleteIngredient() {
        // Arrange
        Long ingredientId = 1L;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);

        Dish dish = new Dish();
        dish.setIngredients(new ArrayList<>());
        dish.getIngredients().add(ingredient);

        ingredient.setDishes(Collections.singletonList(dish)); // Set the ingredient's dishes list

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));
        when(dishRepository.save(any(Dish.class))).thenReturn(dish);

        // Act
        ingredientService.deleteIngredient(ingredientId);

        // Assert
        verify(ingredientRepository, times(1)).delete(ingredient);
        assertEquals(0, dish.getIngredients().size()); // Assert the number of ingredients in the dish is 0
    }

    @Test
    void testDeleteIngredient_ThrowsObjectNotFoundException() {
        // Arrange
        Long ingredientId = 1L;

        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> {
            ingredientService.deleteIngredient(ingredientId);
        });

        verify(ingredientRepository, never()).delete(any(Ingredient.class));
        verify(dishRepository, never()).save(any(Dish.class));
    }


    @Test
    void testDeleteIngredientFromDish_DishNotFound_ObjectNotFoundExceptionThrown() {
        // Arrange
        Long dishId = 1L;
        Long ingredientId = 2L;

        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> {
            ingredientService.deleteIngredientFromDish(dishId, ingredientId);
        });

        verifyNoMoreInteractions(ingredientRepository);
    }

    @Test
    void testDeleteIngredientFromDish_IngredientNotFound_ObjectNotFoundExceptionThrown() {
        // Arrange
        Long dishId = 1L;
        Long ingredientId = 2L;

        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ObjectNotFoundException.class, () -> {
            ingredientService.deleteIngredientFromDish(dishId, ingredientId);
        });
    }
    @Test
    void addExistingIngredientByDishId_ExistingIngredient_SuccessfullyAdded() {
        // Arrange
        Long dishId = 1L;
        Long ingredientId = 2L;

        Dish dish = new Dish();
        Ingredient ingredient = new Ingredient();

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(ingredient));

        // Act
        ingredientService.addExistingIngredientByDishId(dishId, ingredientId);

        // Assert
        assertTrue(dish.getIngredients().contains(ingredient));
        verify(dishRepository, times(1)).findById(dishId);
        verify(ingredientRepository, times(1)).findById(ingredientId);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void addExistingIngredientByDishId_NonExistingDish_ObjectNotFoundExceptionThrown() {
        // Arrange
        Long dishId = 1L;
        Long ingredientId = 2L;

        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class,
                () -> ingredientService.addExistingIngredientByDishId(dishId, ingredientId));
        verify(dishRepository, times(1)).findById(dishId);
        verify(ingredientRepository, never()).findById(anyLong());
        verify(dishRepository, never()).save(any(Dish.class));
    }

    @Test
    void addExistingIngredientByDishId_NonExistingIngredient_ObjectNotFoundExceptionThrown() {
        // Arrange
        Long dishId = 1L;
        Long ingredientId = 2L;

        Dish dish = new Dish();

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dish));
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ObjectNotFoundException.class,
                () -> ingredientService.addExistingIngredientByDishId(dishId, ingredientId));
        verify(dishRepository, times(1)).findById(dishId);
        verify(ingredientRepository, times(1)).findById(ingredientId);
        verify(dishRepository, never()).save(any(Dish.class));
    }
//    @Test
//    void addNewIngredientByDishId_ExistingDishAndNewIngredient_SuccessfullyAdded() {
//        // Arrange
//        Long dishId = 1L;
//        Ingredient ingredient = new Ingredient();
//
//        when(dishRepository.existsById(dishId)).thenReturn(true);
//        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);
//
//        // Act
//        Ingredient result = ingredientService.addNewIngredientByDishId(dishId, ingredient);
//
//        // Assert
//        assertNotNull(result);
//        assertTrue(result.getId() > 0); // Assuming the ID is generated upon saving
//        verify(dishRepository, times(1)).existsById(dishId);
//        verify(ingredientRepository, times(1)).save(ingredient);
//    }

    @Test
    void addNewIngredientByDishId_NonExistingDish_ObjectNotFoundExceptionThrown() {
        // Arrange
        Long dishId = 1L;
        Ingredient ingredient = new Ingredient();

        when(dishRepository.existsById(dishId)).thenReturn(false);

        // Act & Assert
        assertThrows(ObjectNotFoundException.class,
                () -> ingredientService.addNewIngredientByDishId(dishId, ingredient));
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }
}
