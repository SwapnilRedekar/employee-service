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
}
