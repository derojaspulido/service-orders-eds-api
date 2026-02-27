package com.backenEDS.exception;

/**
 * Exception representing business rule violations.
 * Results in HTTP 400 responses.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}