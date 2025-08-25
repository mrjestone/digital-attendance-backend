package com.jestone.attendance.auth;

import com.jestone.attendance.user.User; // Import the User class
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private User user; // ADDED: This field will hold the authenticated user's details.
}