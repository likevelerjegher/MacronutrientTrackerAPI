# kaloriinnhold

## The programm takes `name` and `weight` of the ingredient in grams, then sends bask JSON with its detailed nutritional content.

GET endpoint has been added that accepts input parameters (name/weight) as queryParams in the URL and returns the result (detailed nutritional content of the ingredient) as JSON.
```
http://localhost:8080/dish/calculate?name=(ingredient, you want to look for)&weight=(gramms of the product)
```
