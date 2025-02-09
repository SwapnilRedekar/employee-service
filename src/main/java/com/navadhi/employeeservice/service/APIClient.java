package com.navadhi.employeeservice.service;

import com.navadhi.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080/v1/departments",name = "Department-Service")
public interface APIClient {

    @GetMapping("/{code}")
    DepartmentDto getDepartment(@PathVariable("code") String code);
}
