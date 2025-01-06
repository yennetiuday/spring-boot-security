package com.uday.spring_boot_security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeResource {

    @GetMapping("/")
    public String home() {
        return "<h1>Welcome</h1>";
        // spring-security default credentials:
        // username: user
        // password: copy from console

        // After changing the username and password in application properties
        // The default user credentials will not work.
    }
}
