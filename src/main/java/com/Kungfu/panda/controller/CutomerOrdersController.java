package com.Kungfu.panda.controller;

import java.util.List;
import java.util.Optional;

import com.Kungfu.panda.entity.CutomerOrders;
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

import com.Kungfu.panda.exception.ResourceNotFoundException;
import com.Kungfu.panda.helper.CutomerOrdersHelper;

/**
 * CutomerOrdersController handles all HTTP requests related to CutomerOrders entity.
 * Provides REST endpoints for CRUD operations on CutomerOrders.
 * Exceptions are handled centrally by GlobalExceptionHandler.
 */
@RestController
@RequestMapping("/api/orders")
public class CutomerOrdersController {

    /**
     * Autowired CutomerOrdersHelper service for business logic operations.
     */
    @Autowired
    private CutomerOrdersHelper cutomerOrdersHelper;

    /**
     * GET endpoint to retrieve all customer orders from the database.
     * 
     * @return ResponseEntity containing list of all orders with HTTP status 200
     * @throws DatabaseException if database operation fails
     */
    @GetMapping
    public ResponseEntity<List<CutomerOrders>> getAllOrders() {
        List<CutomerOrders> orders = cutomerOrdersHelper.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * GET endpoint to retrieve a specific customer order by its ID.
     * 
     * @param orderId the ID of the order to retrieve
     * @return ResponseEntity containing the order with HTTP status 200
     * @throws ResourceNotFoundException if order not found
     * @throws DatabaseException if database operation fails
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<CutomerOrders> getOrderById(@PathVariable Long orderId) {
        Optional<CutomerOrders> order = cutomerOrdersHelper.getOrderById(orderId);
        if (!order.isPresent()) {
            throw new ResourceNotFoundException("Order not found with ID: " + orderId);
        }
        return new ResponseEntity<>(order.get(), HttpStatus.OK);
    }

    /**
     * POST endpoint to create a new customer order.
     * 
     * @param order the CutomerOrders object to be created
     * @return ResponseEntity containing the created order with HTTP status 201
     * @throws DatabaseException if database operation fails
     */
    @PostMapping
    public ResponseEntity<CutomerOrders> createOrder(@RequestBody CutomerOrders order) {
        CutomerOrders savedOrder = cutomerOrdersHelper.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

}

