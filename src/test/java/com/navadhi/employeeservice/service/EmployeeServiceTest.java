package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import com.navadhi.employeeservice.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
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
<<<<<<< HEAD

    @Test
    void shouldReturnAllEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(createEmployees());
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals("Amruta", employees.getFirst().firstName());
        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }

    private List<Employee> createEmployees() {
        return List.of(
                new Employee("Amruta", "Kapdeo", "a.k@gmail.com",
                        "C3A", LocalDate.of(1990, 1, 1), 1200000),
                new Employee("Jyoti", "Jain", "j.jain@gmail.com",
                        "C3B", LocalDate.of(1990, 12, 20), 1500000)
        );
=======
    
    @Test
    void shouldReturnEmployeeById() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        Assertions.assertEquals(employeeDto, employeeService.getEmployeeById(10L));
        Mockito.verify(employeeRepository,Mockito.times(1)).findById(10L);
>>>>>>> 6434e30 (Feature to fetch Employee by employee id)
    }
}
