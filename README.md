# kaloriinnhold

"Kaloriinnhold" is a project successfully integrated with MySQL database for efficient data storage and management. A one-to-many relationship using the @OneToMany annotation has been implemented, providing efficient management of linked data. Also implemented many-to-many relationship using @ManyToMany annotation, which significantly expands the possibilities of working with data and its relationships.

CRUD operations are implemented for all entities, providing full and convenient control over information. Users are able to create, read, update and delete records about different dishes and their ingredients. A query mechanism using queryParams is supported for more flexible and accurate data retrieval, and the results are provided in JSON format, providing easy interaction with the application.

Thus, the project not only successfully integrated the database and realized the relationships between entities, but also provided a complete set of CRUD operations for data management, providing users with an intuitive and efficient interface to work about the existing dishes.

GET endpoint has been added that accepts input parameters (`name` and `weight`) as queryParams in the URL and returns the result (detailed nutritional content of the ingredient) as JSON.

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
