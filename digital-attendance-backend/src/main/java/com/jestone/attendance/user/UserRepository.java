package com.jestone.attendance.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

// By extending JpaRepository, we get all standard CRUD methods for free.
// <User, UUID> specifies it's a repository for the User entity, whose ID is of type UUID.
public interface UserRepository extends JpaRepository<User, UUID> {
    // Spring Data JPA will automatically create a query for us from this method name.
    // This allows us to find a user by their email address.
    Optional<User> findByEmail(String email);
}