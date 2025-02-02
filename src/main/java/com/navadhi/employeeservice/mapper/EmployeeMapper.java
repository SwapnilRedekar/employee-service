package com.navadhi.employeeservice.mapper;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeMapper {

    private EmployeeMapper() {

    }

    public static EmployeeDto toEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getEmployeeId(),  employee.getFirstName(),
                employee.getLastName(), employee.getEmail(), employee.getGrade(),
                employee.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                employee.getSalary());
    }

    public static Employee toEmployee(EmployeeDto employeeDto) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataOfBirth = LocalDate.parse(employeeDto.dateOfBirth(), dateFormatter);
        return new Employee(employeeDto.firstName(), employeeDto.lastName(),
                employeeDto.email(), employeeDto.grade(), dataOfBirth, employeeDto.salary());
    }
}
