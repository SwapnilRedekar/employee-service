package com.navadhi.employeeservice.exception;

public class InvalidSortingOrderException extends RuntimeException {

    public InvalidSortingOrderException(String sortingType) {
        super("Given sorting type: " + sortingType + " is not supported.");
    }
}
