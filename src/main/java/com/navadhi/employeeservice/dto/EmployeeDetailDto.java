package com.navadhi.employeeservice.dto;

public record EmployeeDetailDto(String firstName,
                                String lastName,
                                String email,
                                String grade,
                                String dateOfBirth,
                                double salary,
                                DepartmentDto department) {
}
