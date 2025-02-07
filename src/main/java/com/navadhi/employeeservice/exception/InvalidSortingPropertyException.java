package com.navadhi.employeeservice.exception;

public class InvalidSortingPropertyException extends RuntimeException {
    public InvalidSortingPropertyException(String property) {
        super("Given sorting property: " + property + " is not present in Employee Resource");
    }
}
