package com.navadhi.employeeservice.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super("The " + resourceName + " resource is not present for " + fieldName + " of value " + fieldValue);
    }
}
