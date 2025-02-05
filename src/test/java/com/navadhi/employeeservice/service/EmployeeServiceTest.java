package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import com.navadhi.employeeservice.exception.EmailCantBeUpdatedException;
import com.navadhi.employeeservice.exception.ResourceNotFoundException;
import com.navadhi.employeeservice.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
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
    Employee updatedEmployee;

    @Test
    void shouldReturnEmployeeWithGivenEmailAddress() {
        employee = new Employee(1L, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000);
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
        employee = new Employee(null, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000);
        Mockito.when(employeeRepository.saveAndFlush(Mockito.any(Employee.class))).thenReturn(employee);
        Assertions.assertEquals(employeeDto, employeeService.createEmployee(employeeDto));
        Mockito.verify(employeeRepository, Mockito.times(1)).saveAndFlush(employee);
    }

    @Test
    void shouldReturnAllEmployees() {
        Mockito.when(employeeRepository.findAll()).thenReturn(createEmployees());
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        Assertions.assertEquals(2, employees.size());
        Assertions.assertEquals("Amruta", employees.getFirst().firstName());
        Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldReturnEmployeeById() {
        employee = new Employee(10L, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000);
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        Assertions.assertEquals(employeeDto, employeeService.getEmployeeById(10L));
        Mockito.verify(employeeRepository,Mockito.times(1)).findById(10L);
    }

    @Test
    void shouldUpdateEmployeeDetails() {
        employee = new Employee(10L,"Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000);
        updatedEmployee = new Employee(10L, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 2000000);
        employeeDto = new EmployeeDto(10L, "Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                "10-02-1996", 2000000);
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepository.saveAndFlush(Mockito.any(Employee.class))).thenReturn(updatedEmployee);
        Assertions.assertEquals(employeeDto, employeeService.updateEmployee(employeeDto));
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(10L);
        Mockito.verify(employeeRepository, Mockito.times(1)).saveAndFlush(updatedEmployee);
    }

    @Test
    void shouldThrowEmailCantBeUpdatedException() {
        employee = new Employee(10L,"Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000);
        employeeDto = new EmployeeDto(10L, "Avika",
                "Gaur", "avika.gaur@gmail.com", "C3A",
                "10-02-1996", 2000000);
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        Assertions.assertThrows(EmailCantBeUpdatedException.class, () -> employeeService.updateEmployee(employeeDto));
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(10L);
    }

    @Test
    void shouldThrowResourceNotFoundException() {
        employeeDto = new EmployeeDto(100L, "Avika",
                "Gaur", "avika.gaur@gmail.com", "C3A",
                "10-02-1991", 2000000);

        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(employeeDto));
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(100L);
    }

    @Test
    void shouldReturnTruePostDeletingEmployee() {
        employee = new Employee(10L,"Avika",
                "Gaur", "a.gaur@gmail.com", "C3A",
                LocalDate.of(1996, 2, 10), 1500000);
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        Assertions.assertTrue(employeeService.deleteEmployee(10L));
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(10L);
    }

    @Test
    void shouldReturnFalseWhenEmployeeDoesntExists() {
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertFalse(employeeService.deleteEmployee(151L));
        Mockito.verify(employeeRepository, Mockito.times(1)).findById(151L);
    }

    private List<Employee> createEmployees() {
        return List.of(
                new Employee(1L, "Amruta", "Kapdeo", "a.k@gmail.com",
                        "C3A", LocalDate.of(1990, 1, 1), 1200000),
                new Employee(2L, "Jyoti", "Jain", "j.jain@gmail.com",
                        "C3B", LocalDate.of(1990, 12, 20), 1500000)
        );
    }

}
