package com.likevel.kaloriinnhold.service;

import com.likevel.kaloriinnhold.services.RequestCounterService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RequestCounterServiceTest {
    @Test
    void testIncrementRequestCount() {
        // Arrange
        int initialCount = RequestCounterService.getRequestCount();

        // Act
        RequestCounterService.incrementRequestCount();

        // Assert
        assertEquals(initialCount + 1, RequestCounterService.getRequestCount());
    }

    @Test
    void testGetRequestCount() {
        // Arrange
        int expectedCount = RequestCounterService.getRequestCount();

        // Act
        int actualCount = RequestCounterService.getRequestCount();

        // Assert
        assertEquals(expectedCount, actualCount);
    }
    @Test
    void testPrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // Arrange
        Constructor<RequestCounterService> constructor = RequestCounterService.class.getDeclaredConstructor();

        // Act
        constructor.setAccessible(true);
        RequestCounterService instance = constructor.newInstance();

        // Assert
        assertNotNull(instance);
    }
}
