package com.Kungfu.panda.helper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Kungfu.panda.entity.CutomerOrders;
import com.Kungfu.panda.exception.DatabaseException;
import com.Kungfu.panda.exception.ResourceNotFoundException;
import com.Kungfu.panda.repository.CutomerOrdersRepo;

/**
 * CutomerOrdersHelper class is a Spring service that handles all business logic
 * related to CutomerOrders entity. It provides helper methods for managing
 * customer orders throughout the application with exception handling.
 */
@Service
public class CutomerOrdersHelper {

    /**
     * Autowired CutomerOrdersRepo repository for performing CRUD operations
     * on the CutomerOrders entity with the database.
     */
    @Autowired
    private CutomerOrdersRepo cutomerOrdersRepo;

    /**
     * Retrieves all customer orders from the database.
     * 
     * @return List of all CutomerOrders records from the database
     * @throws DatabaseException if database operation fails
     */
    public List<CutomerOrders> getAllOrders() {
        try {
            return cutomerOrdersRepo.findAll();
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve all orders: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a specific customer order by its ID.
     * 
     * @param orderId the ID of the customer order to retrieve
     * @return Optional containing the CutomerOrders if found, empty otherwise
     * @throws ResourceNotFoundException if order not found
     * @throws DatabaseException if database operation fails
     */
    public Optional<CutomerOrders> getOrderById(Long orderId) {
        try {
            return cutomerOrdersRepo.findById(orderId);
        } catch (Exception e) {
            throw new DatabaseException("Failed to retrieve order with ID " + orderId + ": " + e.getMessage(), e);
        }
    }

    /**
     * Saves a new customer order or updates an existing order in the database.
     * 
     * @param order the CutomerOrders object to be saved
     * @return the saved CutomerOrders object with generated ID
     * @throws DatabaseException if database operation fails
     */
    @Transactional
    public CutomerOrders saveOrder(CutomerOrders order) {
        try {
            if (order == null) {
                throw new IllegalArgumentException("Order object cannot be null");
            }
            return cutomerOrdersRepo.save(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order data: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DatabaseException("Failed to save order: " + e.getMessage(), e);
        }
    }

}



