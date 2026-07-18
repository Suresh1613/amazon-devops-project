package com.example;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Amazon DevOps Project Successfully Deployed";
    }

    @GetMapping("/health")
    public String health() {
        return "Application is UP";
    }

}
