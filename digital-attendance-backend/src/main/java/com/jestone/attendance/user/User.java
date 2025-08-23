package com.jestone.attendance.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

// Lombok annotations to auto-generate getters, setters, constructors, etc.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// JPA annotations to define this as a database entity
@Entity
// Specifies the table name will be "users".
@Table(name = "users")
public class User implements UserDetails { // Add "implements UserDetails"

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // nullable=false makes this a required column in the database.
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // unique=true ensures no two users can share the same email.
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Tells JPA to store the enum value as a String (e.g., "ATTENDEE").
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    // -------- UserDetails interface methods implementation --------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assume your UserRole enum names correspond to roles like "ROLE_USER", etc.
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        // Use email as the username for Spring Security authentication
        return email;
    }

    @Override
    public String getPassword() {
        // Return the user's password field
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Define your own logic if needed
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Define your own logic if needed
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Define your own logic if needed
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Define your own logic if needed
        return true;
    }
}
