package com.navadhi.employeeservice.controller;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.exception.EmailAlreadyExistsException;
import com.navadhi.employeeservice.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @Autowired
    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<EmployeeDto> createEmployee(@Validated @RequestBody EmployeeDto employeeDto) {
        EmployeeDto existingEmployee = employeeService.findByEmail(employeeDto.email());

        if (Objects.nonNull(existingEmployee))
            throw new EmailAlreadyExistsException("Employee with given email address is already present");

        EmployeeDto persistedEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.ok(persistedEmployee);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<EmployeeDto>> getEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return employees.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(employees);
    }



}
