package com.navadhi.employeeservice.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        LocalDateTime timestamp,
        HttpStatus status,
        String message,
        String path
) {
}
