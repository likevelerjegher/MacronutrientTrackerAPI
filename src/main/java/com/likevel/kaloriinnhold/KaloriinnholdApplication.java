package com.likevel.kaloriinnhold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = "com.likevel.kaloriinnhold")

@RestController
public class KaloriinnholdApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaloriinnholdApplication.class, args);
    }
}
