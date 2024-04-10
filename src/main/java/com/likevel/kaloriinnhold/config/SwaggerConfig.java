package com.likevel.kaloriinnhold.config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;

@OpenAPIDefinition(
        info = @Info(
                title = "Dish nutrition API",
                description = "Calculating the dish nutrition.",
                version = "1.0",
                contact = @Contact(
                        name = "likevelerjegher",
                        email = "neshamanemail@gmail.com"
                )
        )
)
public class SwaggerConfig {
}
