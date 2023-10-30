package com.gringots.controller;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;
import com.gringots.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public CommonResponseDto createEmployee (@RequestBody EmployeeRequestDto employeeRequestDto) throws SQLException {
        employeeService.createEmployee(employeeRequestDto);

        return null;

    }
}
