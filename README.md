# kaloriinnhold

This repository contains a simple REST API application for tracking calorie counts of meals. Users can retrieve information about comments, dishes, and ingredients, and perform add, update, and delete operations on the data.

# Functionality
- Retrieving information about comments, dishes, and ingredients.
- Storing data in a database.
- Operations of adding, updating, and deleting data.

# Technologies used
- Spring Boot: Framework for creating REST API.
- Spring Data JPA: A data access framework for interacting with the database.
- H2 Database: An embedded database for local development.

# Getting Started
## Required components
Make sure you have the following components installed:
- Java 21
- Maven

# Installation
1. Clone the repository:

```console
git clone https://github.com/likevelerjegher/kaloriinnhold
```
2. Run the application
```console
cd kaloriinnhold-api
./gradlew bootRun
```
The application will run on `http://localhost:8080`.

# Usage
## Endpoints
### CommentController
- `GET /api/comments`: get information about comments
- `GET /api/dishes/{dishId}/comments`: get comments for a dish
- `POST /api/dishes/{dishId}/comments`: add a new comment for a dish
- `PUT /api/comments/{id}`: update a comment
- `DELETE /api/dishes/{dishId}/comments`: delete comments for a dish
- `DELETE /api/dishes/{dishId}/comments/{commentId}`: delete a specific comment for a dish
  
### DishController  
- `GET /api`: get dish information
- `GET /api/{id}`: get a dish by ID
- `POST /api`: create a new dish
- `PUT /api/{id}`: update a dish
- `DELETE /api/{id}`: delete a dish
- `DELETE /api`: delete all dishes
  
### IngredientController    
- `GET /api/dishes/{dishId}/ingredients`: get ingredients for a dish
- `GET /api/ingredients/{ingredientId}/dishes`: get dishes by ingredient
- `GET /api/ingredients`: get ingredient information
- `POST /api/dishes/{dishId}/ingredients`: add a new ingredient to a dish
- `PUT /api/ingredients/{id}`: update an ingredient
- `DELETE /api/ingredients/{id}`: delete ingredient
- `DELETE /api/dishes/{dishId}/ingredients/{ingredientId}`: remove an ingredient from a dish

# Remarks
If you've made it this far, you're indeed interested in this project, I appreciate it. 
Well, since that's the case, I can't miss the opportunity to recommend the playlist of my recent music favorites. 
```
https://open.spotify.com/playlist/7dgLdS9xl9U2ZU4QHmXnQ8?si=4c7108f4d4d24578
```
If you like it, do not hesitate to save it.
