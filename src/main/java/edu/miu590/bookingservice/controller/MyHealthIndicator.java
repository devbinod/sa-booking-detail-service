package edu.miu590.bookingservice.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MyHealthIndicator implements HealthIndicator {

    @Override
    @GetMapping({"/","/health"})
    public Health health() {
        return Health.up().build();
    }
}
