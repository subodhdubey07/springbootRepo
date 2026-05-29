package com.Kungfu.panda.exception;

/**
 * Custom exception for database operation errors.
 */
public class DatabaseException extends RuntimeException {

    /**
     * Constructs DatabaseException with a detail message.
     * 
     * @param message the detail message
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructs DatabaseException with a detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

