package com.gcu.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gcu.taskmanager.model.User;

/**
 * Repository interface for performing database operations on User entities.
 * This interface extends JpaRepository so that standard CRUD operations
 * are automatically provided by Spring Data JPA.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    /**
     * Finds a user by username.
     *
     * @param username The username to search for
     * @return The matching User object, or null if not found
     */
    public User findByUsername(String username);

    /**
     * Finds a user by email address.
     *
     * @param email The email address to search for
     * @return The matching User object, or null if not found
     */
    public User findByEmail(String email);
}