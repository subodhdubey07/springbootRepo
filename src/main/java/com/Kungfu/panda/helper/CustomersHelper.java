package com.Kungfu.panda.helper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Kungfu.panda.entity.Customers;
import com.Kungfu.panda.exception.DatabaseException;
import com.Kungfu.panda.exception.ResourceNotFoundException;
import com.Kungfu.panda.repository.CustomersRepo;

/**
 * CustomersHelper class is a Spring service that handles all business logic
 * related to Customers entity. It acts as a middleman between the controller
 * and repository layers with exception handling.
 */
@Service
public class CustomersHelper {

    /**
     * Autowired CustomersRepo repository for performing CRUD operations
     * on the Customers entity with the database.
     */
    @Autowired
    private CustomersRepo customersRepo;

    /**
     * Retrieves all customers from the database.
     * 
     * @return List of all Custoomers records from the database
     * @throws DatabaseException if database operation fails
     */
    public List<Customers> getAllCustomers() {
        try {
            return customersRepo.findAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve all customers: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a specific customer by their customer ID.
     * 
     * @param customerId the ID of the customer to retrieve
     * @return Optional containing the Custoomers if found, empty otherwise
     * @throws ResourceNotFoundException if customer not found
     * @throws DatabaseException if database operation fails
     */
    public Optional<Customers> getCustomerById(Long customerId) {
        try {
            return customersRepo.findById(customerId);
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve customer with ID " + customerId + ": " + e.getMessage(), e);
        }
    }

    /**
     * Saves a new customer or updates an existing customer in the database.
     * 
     * @param customer the Custoomers object to be saved
     * @return the saved Custoomers object with generated ID
     * @throws DatabaseException if database operation fails
     */
    @Transactional
    public Customers saveCustomer(Customers customer) {
        try {
            if (customer == null) {
                throw new IllegalArgumentException("Customer object cannot be null");
            }
            return customersRepo.save(customer);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid customer data: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DatabaseException("Failed to save customer: " + e.getMessage(), e);
        }
    }

}



