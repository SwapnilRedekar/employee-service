package com.navadhi.employeeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navadhi.employeeservice.dto.EmployeeDto;
import com.navadhi.employeeservice.entity.Employee;
import com.navadhi.employeeservice.service.IEmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@AutoConfigureMockMvc
@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEmployeeService employeeService;

    Employee employee;
    EmployeeDto employeeDto;
    EmployeeDto persistedEmployeeDto;

    @Test
    void shouldReturnHttpStatusBadRequestForEmptyEmail() throws Exception {
        employeeDto = new EmployeeDto(null, "Avika",
                "Gaur", "", "C3A",
                "10-02-1996", 1500000);
        mockMvc.perform(MockMvcRequestBuilders
                   .post("/v1/employees")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(toJsonString(employeeDto))
                   .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.email")
                         .value("Email can't be null/blank"));
    }

    @Test
    void shouldReturnHttpStatusBadRequestForNonCompliantDateFormat() throws Exception {
        employeeDto = new EmployeeDto(null, "Avika",
                "Gaur", "a.guar@gmail.com", "C3A",
                "10-02-96", 1500000);
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(employeeDto))
                    .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth")
                         .value("Not compliant with date format dd-mm-yyyy"));
    }

    @Test
    void shouldReturnHttpStatusBadRequestForNegativeSalary() throws Exception {
        employeeDto = new EmployeeDto(null, "Avika",
                "Gaur", "a.guar@gmail.com", "C3A",
                "10-02-1996", -1500000);
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(employeeDto))
                    .accept(MediaType.APPLICATION_JSON)
               ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary")
                        .value("Salary can't be negative"));
    }

    @Test
    void shouldReturnHttpStatusBadRequestForExistingEmailAddress() throws Exception {
        employeeDto = new EmployeeDto(100L, "Avika",
                "Gaur", "a.guar@gmail.com", "C3A",
                "10-02-1996", 1500000);
        Mockito.when(employeeService.findByEmail(Mockito.anyString())).thenReturn(employeeDto);
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(employeeDto))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Employee with given email address is already present"));
        Mockito.verify(employeeService,Mockito.times(1)).findByEmail("a.guar@gmail.com");
    }

    @Test
    void shouldReturnHttpStatusOkAndSaveEmployee() throws Exception {
        employeeDto = new EmployeeDto(null, "Avika",
                "Gaur", "a.guar@gmail.com", "C3A",
                "10-02-1996", 1500000);
        persistedEmployeeDto = new EmployeeDto(10L, "Avika",
                "Gaur", "a.guar@gmail.com", "C3A",
                "10-02-1996", 1500000);
        Mockito.when(employeeService.createEmployee(employeeDto)).thenReturn(persistedEmployeeDto);
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toJsonString(employeeDto))
                    .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId")
                         .value("10"));
        Mockito.verify(employeeService, Mockito.times(1)).createEmployee(employeeDto);
    }

    @Test
    void shouldReturnHttpStatusOkAndAllEmployees() throws Exception {
        Mockito.when(employeeService.getAllEmployees()).thenReturn(createEmployees());
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[:1].firstName")
                        .value("Amruta"));
        Mockito.verify(employeeService, Mockito.times(1)).getAllEmployees();
    }

    @Test
    void shouldReturnHttpStatusNoContent() throws Exception {
        Mockito.when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/employees"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(employeeService, Mockito.times(1)).getAllEmployees();
    }

    @Test
    void shouldReturnHttpStatusOkAndEmployeeWithGivenId() throws Exception {
        employeeDto = new EmployeeDto(10L, "Avika",
                "Gaur", "a.guar@gmail.com", "C3A",
                "10-02-1996", 1500000);
        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong())).thenReturn(employeeDto);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/employees/10"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName")
                        .value("Avika"));

    }

    @Test
    void shouldReturnHttpStatusNotFound() throws Exception {
        Mockito.when(employeeService.getEmployeeById(Mockito.anyLong())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/employees/10"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("The Employee resource is not present for Employee Id of value 10"));
    }

    private String toJsonString(EmployeeDto employeeDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        String result = null;
        try {
            result = objectMapper.writeValueAsString(employeeDto);
        } catch (Exception ex) {
            System.out.println("Exception while converting to JSON string.");
        }
        return result;
    }

    private List<EmployeeDto> createEmployees() {
        return List.of(
                new EmployeeDto(1123L, "Amruta", "Kapdeo", "a.k@gmail.com",
                        "C3A", "01-01-1990", 1200000),
                new EmployeeDto(1121L, "Jyoti", "Jain", "j.jain@gmail.com",
                        "C3B", "20-12-1990", 1500000)
        );
    }
}
