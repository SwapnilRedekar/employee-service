package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto findByEmail(String email);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(long id);

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    boolean deleteEmployee(long id);

    List<EmployeeDto> getAllEmployeesPaginated(String property, String order, int size, int page);
}
