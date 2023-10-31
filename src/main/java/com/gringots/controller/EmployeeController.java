package com.gringots.controller;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;
import com.gringots.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value="/login/{nic}", method = RequestMethod.GET)
    public CommonResponseDto login(@PathVariable String nic){
        CommonResponseDto commonResponseDto;
        try {
            return employeeService.login(nic);
        } catch (SQLException e) {
            commonResponseDto = new CommonResponseDto();
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
            return commonResponseDto;
        }
    }

}
