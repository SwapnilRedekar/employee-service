package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto findByEmail(String email);

<<<<<<< HEAD
    List<EmployeeDto> getAllEmployees();
=======
    EmployeeDto getEmployeeById(long id);
>>>>>>> 6434e30 (Feature to fetch Employee by employee id)
}
