package com.uday.spring_boot_security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // Define in-memory users
        UserDetails user = User.builder()
                .username("blah")
                .password(passwordEncoder.encode("blah")) // Password is encoded
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin")) // Password is encoded
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use BCryptPasswordEncoder for password encoding
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF (if not needed)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasRole("ADMIN") // Only ADMIN role can access /admin/**
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")   // Only USER role can access /user/**
                        .requestMatchers("/").permitAll()     // Everyone can access /public/**
                        .anyRequest().authenticated()                  // All other requests require authentication
                )
                .formLogin(form -> form.defaultSuccessUrl("/", true)) // Form-based login with default success URL
                .httpBasic(basic -> {}); // Enable HTTP Basic authentication

        return http.build();
    }
}
