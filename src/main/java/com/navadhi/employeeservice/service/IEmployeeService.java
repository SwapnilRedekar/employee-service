package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDetailDto;
import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;

import java.util.List;

import java.util.List;

public interface IEmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto findByEmail(String email);

    List<EmployeeDto> getAllEmployees();

    EmployeeDetailDto getEmployeeById(long id);

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    boolean deleteEmployee(long id);

    List<EmployeeDto> getAllEmployeesPaginated(String property, String order, int size, int page);

}
