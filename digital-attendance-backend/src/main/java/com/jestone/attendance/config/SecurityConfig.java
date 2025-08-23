package com.jestone.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Bean exposes this method's return value as a Spring Bean.
    // This is the standard password encoder for securely hashing passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This bean defines the security rules for our API.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS using the corsConfigurationSource bean below.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disable CSRF (Cross-Site Request Forgery) protection.
                // This is common for stateless REST APIs that use tokens for authentication.
                .csrf(csrf -> csrf.disable())
                // Configure session management to be stateless.
                // The server will not create or maintain any HTTP sessions.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define authorization rules for different endpoints.
                .authorizeHttpRequests(auth -> auth
                        // Permit all requests to our authentication endpoints.
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // All other requests must be authenticated.
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    // This bean configures CORS (Cross-Origin Resource Sharing).
    // It's crucial for allowing our frontend (running on localhost:3000)
    // to make requests to our backend (running on localhost:8080).
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow requests from our Next.js frontend development server.
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Allow common HTTP methods.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow specific headers.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this CORS configuration to all paths in our API.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}