package com.Kungfu.panda.exception;

/**
 * Custom exception for resource not found scenarios.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs ResourceNotFoundException with a detail message.
     * 
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs ResourceNotFoundException with a detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

