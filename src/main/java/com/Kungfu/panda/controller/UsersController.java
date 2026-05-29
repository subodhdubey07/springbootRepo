package com.Kungfu.panda.controller;

import java.util.List;
import java.util.Optional;

import com.Kungfu.panda.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Kungfu.panda.entity.Users;
import com.Kungfu.panda.exception.ResourceNotFoundException;
import com.Kungfu.panda.helper.UsersHelper;

/**
 * UsersController handles all HTTP requests related to Users entity.
 * Provides REST endpoints for CRUD operations on Users.
 * Exceptions are handled centrally by GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/users")
public class UsersController {

    /**
     * Autowired UsersHelper service for business logic operations.
     */
    @Autowired
    private UsersHelper usersHelper;

    /**
     * GET endpoint to retrieve all users from the database.
     * 
     * @return ResponseEntity containing list of all users with HTTP status 200
     * @throws DatabaseException if database operation fails
     */
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersHelper.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve a specific user by their ID.
     * 
     * @param userId the ID of the user to retrieve
     * @return ResponseEntity containing the user with HTTP status 200
     * @throws ResourceNotFoundException if user not found
     * @throws DatabaseException if database operation fails
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Long userId) {
        Optional<Users> user = usersHelper.getUserById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new user.
     * 
     * @param user the Users object to be created
     * @return ResponseEntity containing the created user with HTTP status 201
     * @throws DatabaseException if database operation fails
     */
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users savedUser = usersHelper.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}

