package com.jestone.attendance.auth;

import com.jestone.attendance.security.JwtService;
import com.jestone.attendance.user.User;
import com.jestone.attendance.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        // STEP 1: Check if the user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            // If the Optional contains a user, throw an exception.
            throw new IllegalStateException("User with email " + request.getEmail() + " already exists.");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);

        // For simplicity, we don't return a token on register, just success.
        // A common alternative is to also log them in and return a token here.
        return AuthResponse.builder().token(null).build(); // No token on register
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        // If the above line doesn't throw an exception, the user is authenticated.
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); // Should not happen if authenticated

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }
}