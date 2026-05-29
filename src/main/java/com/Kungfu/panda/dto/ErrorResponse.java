package com.Kungfu.panda.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ErrorResponse DTO for returning error details in API responses.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    /**
     * HTTP status code of the error response.
     */
    private int status;

    /**
     * Error message describing the issue.
     */
    private String message;

    /**
     * Timestamp when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * The API path where the error occurred.
     */
    private String path;
}

