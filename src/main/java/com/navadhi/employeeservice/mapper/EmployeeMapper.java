package com.navadhi.employeeservice.mapper;

import com.navadhi.employeeservice.dto.DepartmentDto;
import com.navadhi.employeeservice.dto.EmployeeDetailDto;
import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeMapper {

    private static final String PATTERN = "dd-MM-yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    private EmployeeMapper() {

    }

    public static EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getEmployeeId(),  employee.getFirstName(),
                employee.getLastName(), employee.getEmail(), employee.getGrade(),
                employee.getDateOfBirth().format(DateTimeFormatter.ofPattern(PATTERN)),
                employee.getSalary(), employee.getDepartmentCode());
    }

    public static Employee toEmployee(EmployeeDto employeeDto) {
        LocalDate dataOfBirth = LocalDate.parse(employeeDto.dateOfBirth(), DATE_FORMATTER);
        return new Employee(employeeDto.employeeId(), employeeDto.firstName(),
                employeeDto.lastName(),
                employeeDto.email(),
                employeeDto.grade(),
                dataOfBirth,
                employeeDto.salary(),
                employeeDto.departmentCode());
    }

    public static EmployeeDetailDto toEmployeeDetailDto(DepartmentDto department,
                                                        Employee employee) {
        return new EmployeeDetailDto(
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getGrade(),
                employee.getDateOfBirth().format(DateTimeFormatter.ofPattern(PATTERN)),
                employee.getSalary(),
                department
        );
    }
}
