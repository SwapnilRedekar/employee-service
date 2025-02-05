package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import com.navadhi.employeeservice.exception.EmailCantBeUpdatedException;
import com.navadhi.employeeservice.exception.ResourceNotFoundException;
import com.navadhi.employeeservice.mapper.EmployeeMapper;
import com.navadhi.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeDto.employeeId());
        EmployeeDto updatedEmployeeDto;
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            if (employee.getEmail().equals(employeeDto.email())) {
                employee = new Employee(employeeDto.employeeId(), employeeDto.firstName(),
                        employeeDto.lastName(), employeeDto.email(), employeeDto.grade(),
                        LocalDate.parse(employeeDto.dateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        employeeDto.salary());
                employee = employeeRepository.saveAndFlush(employee);
                updatedEmployeeDto = EmployeeMapper.toEmployeeDto(employee);
            } else {
                throw new EmailCantBeUpdatedException("Email attribute of employee can't be updated");
            }
        } else
            throw new ResourceNotFoundException("Employee", "Employee Id", String.valueOf(employeeDto.employeeId()));

        return updatedEmployeeDto;
    }

    @Override
    public boolean deleteEmployee(long id) {
        boolean isDeleted = false;
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
            isDeleted = true;
        }
        return isDeleted;
    }
}
