package com.jestone.attendance.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class User {

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
}