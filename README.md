# kaloriinnhold

## This repository contains the source code for the Kaloriinnhold application, which is a Java-based backend service that calculates nutritional data for dishes using the Edamam API.

The programm takes `name` and `weight` of the ingredient in grams, then sends bask JSON with its detailed nutritional content.

GET endpoint has been added that accepts input parameters (name/weight) as queryParams in the URL and returns the result (detailed nutritional content of the ingredient) as JSON.

```
http://localhost:8080/dish/calculate?name=(ingredient, you want to look for)&weight=(weight of the ingredient)
```
# DishController

The DishController class is responsible for handling incoming HTTP requests related to dish calculations. It utilizes the DishService to retrieve nutritional data based on dish name and weight.

## Endpoints
+ GET /calculate
  + Parameters:
    + `name` (String): The name of the dish.
    + `weight` (Long): The weight of the dish.
  +Returns the nutritional data for the specified dish in response to the provided parameters.

# DishService

The DishService class is a service layer that communicates with the Edamam API to fetch nutritional data for dishes. It also interacts with the DishRepository for database operations related to dishes.

## Methods
+ getNutritionalData
  + Parameters:
    + `name` (String): The name of the dish.
    + `weight` (Long): The weight of the dish.
  + Returns the nutritional data for the specified dish by making a request to the Edamam API.
+ allDishes
  + Returns a list of all dishes stored in the database.
+ singleDish
  + Parameters:
    + `name` (String): The name of the dish.
  + Returns an optional containing the dish with the specified name.
 
#Configuration
+ appId and appKey
  + These properties are injected using @Value annotation from the application properties. They represent the credentials required for accessing the Edamam API.
    
# Usage
To use this application, make sure to configure the Edamam API credentials (`appId` and `appKey`) in your application properties file. Then, you can run the application and access the `/calculate` endpoint to get nutritional data for a specific dish.

# Dependencies
+ Spring Boot
+ Spring Web
+ Spring Data JPA
+ RestTemplate
