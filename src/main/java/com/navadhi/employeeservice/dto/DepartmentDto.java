package com.navadhi.employeeservice.dto;

public record DepartmentDto(Long departmentId,
                            String departmentName,
                            String departmentDescription,
                            String departmentCode) {
}
