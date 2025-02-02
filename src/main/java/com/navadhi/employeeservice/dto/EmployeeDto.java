package com.navadhi.employeeservice.dto;

import jakarta.validation.constraints.*;

import java.util.Objects;

public record EmployeeDto(Long employeeId,
                          @NotBlank(message = "First Name can't be null/blank")
                          String firstName,
                          @NotBlank(message = "Last Name can't be null/blank")
                          String lastName,
                          @NotBlank(message = "Email can't be null/blank")
                          @Email(message = "Not compliant with email format")
                          String email,
                          @NotBlank(message = "Grade can't be null/blank")
                          String grade,
                          @NotBlank(message = "Date of Birth can't be null/blank")
                          @Pattern(message = "Not compliant with date format dd-mm-yyyy",
                          regexp = "^(0?[\\d]|[1|2][\\d]|3[0|1])-(0?[\\d]|1[0-2])-([\\d]{4})$")
                          String dateOfBirth,
                          @Positive(message = "Salary can't be negative")
                          double salary) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDto that)) return false;
        return Double.compare(salary, that.salary) == 0 && Objects.equals(email, that.email) && Objects.equals(grade, that.grade) && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(dateOfBirth, that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, grade, dateOfBirth, salary);
    }
}