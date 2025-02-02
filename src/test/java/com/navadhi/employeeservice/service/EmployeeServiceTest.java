package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import com.navadhi.employeeservice.repository.EmployeeRepository;
import com.navadhi.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    Employee employee;
    EmployeeDto employeeDto;

    @BeforeEach
    public void init() {
        employeeDto = new EmployeeDto(null, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                "10-02-1996", 1500000);
        employee = new Employee("Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000);
    }

    @Test
    void shouldReturnEmployeeWithGivenEmailAddress() {
        Mockito.when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(employee));
        Assertions.assertNotNull(employeeService.findByEmail("a.gaur@gmail.com"));
        Mockito.verify(employeeRepository, Mockito.times(1)).findByEmail("a.gaur@gmail.com");
    }

    @Test
    void shouldReturnNoEmployeeWithGivenEmailAddress() {
        Mockito.when(employeeRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertNull(employeeService.findByEmail("s.patil@gmail.com"));
        Mockito.verify(employeeRepository, Mockito.times(1)).findByEmail("s.patil@gmail.com");
    }

    @Test
    void shouldSaveEmployee() {
        Mockito.when(employeeRepository.saveAndFlush(Mockito.any(Employee.class))).thenReturn(employee);
        Assertions.assertEquals(employeeDto, employeeService.createEmployee(employeeDto));
        Mockito.verify(employeeRepository, Mockito.times(1)).saveAndFlush(employee);
    }
}
