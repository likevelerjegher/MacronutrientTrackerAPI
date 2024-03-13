# kaloriinnhold

This repository contains a simple REST API application for tracking calorie counts of meals. Users can retrieve information about comments, dishes, and ingredients, and perform add, update, and delete operations on the data.

## Functionality
- Retrieving information about comments, dishes, and ingredients.
- Storing data in a database.
- Operations of adding, updating, and deleting data.

## Technologies used
- Spring Boot: Framework for creating REST API.
- Spring Data JPA: A data access framework for interacting with the database.
- H2 Database: An embedded database for local development.

## Getting Started
## Required components
Make sure you have the following components installed:
- Java 21
- Maven

## Installation
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

## Usage
## Endpoints

- `GET /api/comments`: get information about comments
- `GET /api/dishes/{dishId}/comments`: get comments for a dish
- `POST /api/dishes/{dishId}/comments`: add a new comment for a dish
- `PUT /api/comments/{id}`: Update a comment
- Delete comments for a dish: DELETE /api/dishes/{dishId}/comments
- Delete a specific comment for a dish: DELETE /api/dishes/{dishId}/comments/{commentId}
- Get dish information: GET /api
- Get a dish by ID: GET /api/{id}
- Create a new dish: POST /api
- Update a dish: PUT /api/{id}
- Delete a dish: DELETE /api/{id}
- Delete all dishes: DELETE /api
- Get ingredients for a dish: GET /api/dishes/{dishId}/ingredients
- Get dishes by ingredient: GET /api/ingredients/{ingredientId}/dishes
- Get ingredient information: GET /api/ingredients
- Add a new ingredient to a dish: POST /api/dishes/{dishId}/ingredients
- Update an ingredient: PUT /api/ingredients/{id}
- Delete ingredient: DELETE /api/ingredients/{id}
- Remove an ingredient from a dish: DELETE /api/dishes/{dishId}/ingredients/{ingredientId}
