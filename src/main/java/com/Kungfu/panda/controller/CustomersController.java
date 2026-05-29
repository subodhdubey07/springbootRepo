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

import com.Kungfu.panda.entity.Customers;
import com.Kungfu.panda.exception.ResourceNotFoundException;
import com.Kungfu.panda.helper.CustomersHelper;

/**
 * CustomersController handles all HTTP requests related to Customers entity.
 * Provides REST endpoints for CRUD operations on Customers.
 * Exceptions are handled centrally by GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomersController {

    /**
     * Autowired CustomersHelper service for business logic operations.
     */
    @Autowired
    private CustomersHelper customersHelper;

    /**
     * GET endpoint to retrieve all customers from the database.
     * 
     * @return ResponseEntity containing list of all customers with HTTP status 200
     * @throws DatabaseException if database operation fails
     */
    @GetMapping
    public ResponseEntity<List<Customers>> getAllCustomers() {
        List<Customers> customers = customersHelper.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve a specific customer by their ID.
     * 
     * @param customerId the ID of the customer to retrieve
     * @return ResponseEntity containing the customer with HTTP status 200
     * @throws ResourceNotFoundException if customer not found
     * @throws DatabaseException if database operation fails
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Customers> getCustomerById(@PathVariable Long customerId) {
        Optional<Customers> customer = customersHelper.getCustomerById(customerId);
        if (!customer.isPresent()) {
            throw new ResourceNotFoundException("Customer not found with ID: " + customerId);
        }
        return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new customer.
     * 
     * @param customer the Custoomers object to be created
     * @return ResponseEntity containing the created customer with HTTP status 201
     * @throws DatabaseException if database operation fails
     */
    @PostMapping
    public ResponseEntity<Customers> createCustomer(@RequestBody Customers customer) {
        Customers savedCustomer = customersHelper.saveCustomer(customer);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

}

