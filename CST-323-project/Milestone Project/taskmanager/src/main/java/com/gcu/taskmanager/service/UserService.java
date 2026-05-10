package com.gcu.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcu.taskmanager.model.User;
import com.gcu.taskmanager.repository.UserRepository;

/**
 * Service class that contains the business logic for User operations.
 * This class handles user registration, login validation, and lookup operations.
 */
@Service
public class UserService
{
    /**
     * Repository used to perform database operations for User entities.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Registers a new user in the database if the username and email
     * are not already in use.
     *
     * @param user The user to register
     * @return true if the user was successfully registered, false otherwise
     */
    public boolean registerUser(User user)
    {
        if (userRepository.findByUsername(user.getUsername()) != null)
        {
            return false;
        }

        if (userRepository.findByEmail(user.getEmail()) != null)
        {
            return false;
        }

        userRepository.save(user);
        return true;
    }

    /**
     * Validates a user's login credentials.
     *
     * @param username The username entered by the user
     * @param password The password entered by the user
     * @return true if the credentials are valid, false otherwise
     */
    public boolean validateLogin(String username, String password)
    {
        User user = userRepository.findByUsername(username);

        if (user == null)
        {
            return false;
        }

        return user.getPassword().equals(password);
    }

    /**
     * Finds a user by username.
     *
     * @param username The username to search for
     * @return The matching User object, or null if not found
     */
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks whether a username already exists in the database.
     *
     * @param username The username to check
     * @return true if the username exists, false otherwise
     */
    public boolean usernameExists(String username)
    {
        return userRepository.findByUsername(username) != null;
    }

    /**
     * Checks whether an email already exists in the database.
     *
     * @param email The email address to check
     * @return true if the email exists, false otherwise
     */
    public boolean emailExists(String email)
    {
        return userRepository.findByEmail(email) != null;
    }
}