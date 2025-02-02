package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;

public interface IEmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto findByEmail(String email);
}
