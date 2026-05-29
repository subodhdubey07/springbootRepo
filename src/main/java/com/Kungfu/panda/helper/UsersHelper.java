package com.Kungfu.panda.helper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Kungfu.panda.entity.Users;
import com.Kungfu.panda.exception.DatabaseException;
import com.Kungfu.panda.exception.ResourceNotFoundException;
import com.Kungfu.panda.repository.UsersRepo;

/**
 * UsersHelper class is a Spring service that handles all business logic
 * related to Users entity. It serves as a service layer for user-related
 * operations and database interactions with exception handling.
 */
@Service
public class UsersHelper {

    /**
     * Autowired UsersRepo repository for performing CRUD operations
     * on the Users entity with the database.
     */
    @Autowired
    private UsersRepo usersRepo;

    /**
     * Retrieves all users from the database.
     * 
     * @return List of all Users records from the database
     * @throws DatabaseException if database operation fails
     */
    public List<Users> getAllUsers() {
        try {
            return usersRepo.findAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve all users: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a specific user by their user ID.
     * 
     * @param userId the ID of the user to retrieve
     * @return Optional containing the Users if found, empty otherwise
     * @throws ResourceNotFoundException if user not found
     * @throws DatabaseException if database operation fails
     */
    public Optional<Users> getUserById(Long userId) {
        try {
            return usersRepo.findById(userId);
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve user with ID " + userId + ": " + e.getMessage(), e);
        }
    }

    /**
     * Saves a new user or updates an existing user in the database.
     * 
     * @param user the Users object to be saved
     * @return the saved Users object with generated ID
     * @throws DatabaseException if database operation fails
     */
    @Transactional
    public Users saveUser(Users user) {
        try {
            if (user == null) {
                throw new IllegalArgumentException("User object cannot be null");
            }
            return usersRepo.save(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid user data: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DatabaseException("Failed to save user: " + e.getMessage(), e);
        }
    }

}



