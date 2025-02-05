package com.navadhi.employeeservice.exception;

public class EmailCantBeUpdatedException extends RuntimeException {

    public EmailCantBeUpdatedException(String message) {
        super(message);
    }
}
