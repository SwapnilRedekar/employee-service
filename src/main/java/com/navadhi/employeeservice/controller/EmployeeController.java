package com.navadhi.employeeservice.controller;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import com.navadhi.employeeservice.exception.EmailAlreadyExistsException;
import com.navadhi.employeeservice.exception.ResourceNotFoundException;
import com.navadhi.employeeservice.service.IEmployeeService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") long id) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);

        if(Objects.isNull(employeeDto))
            throw new ResourceNotFoundException("Employee", "Employee Id", String.valueOf(id));

        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody @Validated EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable("id") Long id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        if(isDeleted)
            return ResponseEntity.noContent().build();
        else
            throw new ResourceNotFoundException("Employee", "Employee Id", String.valueOf(id));
    }

    @GetMapping("/q")
    public ResponseEntity<List<EmployeeDto>> getAllEmployeesInSortingOrderByProperties (
            @NotBlank @RequestParam("property") String property,
            @NotBlank @RequestParam("sortType") String sortType) throws NoSuchFieldException{
         List<EmployeeDto> employees = employeeService.getAllEmployeesInSortingOrderByProperties(property, sortType);
         return ResponseEntity.ok(employees);
    }



}
