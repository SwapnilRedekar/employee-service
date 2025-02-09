package com.navadhi.employeeservice.mapper;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class EmployeeMapperTest {

    EmployeeDto employeeDto;
    Employee employee;
    @BeforeEach
    public void init() {
        employeeDto = new EmployeeDto(null, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                "10-02-1996", 1500000, "EC101");
        employee = new Employee(null, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000, "EC101");
    }

    @Test
    void shouldReturnEmployeeEntity() {
        Assertions.assertEquals(employee, EmployeeMapper.toEmployee(employeeDto));
    }

    @Test
    void shouldReturnEmployeeDto() {
        Assertions.assertEquals(employeeDto, EmployeeMapper.toEmployeeDto(employee));
    }
}
