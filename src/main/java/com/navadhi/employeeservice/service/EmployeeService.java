package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import com.navadhi.employeeservice.mapper.EmployeeMapper;
import com.navadhi.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
         Employee employee = EmployeeMapper.toEmployee(employeeDto);
         employee = employeeRepository.saveAndFlush(employee);
         return EmployeeMapper.toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto findByEmail(String email) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
        return optionalEmployee.map(EmployeeMapper::toEmployeeDto)
                .orElse(null);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toEmployeeDto)
                .toList();
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        return employeeRepository.findById(id)
                .map(EmployeeMapper::toEmployeeDto)
                .orElse(null);
    }
}
