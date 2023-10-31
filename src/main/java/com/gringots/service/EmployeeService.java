package com.gringots.service;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import com.gringots.model.request.EmployeeRequestDto;
import com.gringots.model.response.EmployeeResponseDto;

import java.sql.SQLException;

public interface EmployeeService {

    CommonResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) throws SQLException;
    CommonResponseDto login(String nic) throws SQLException;

    EmployeeResponseDto getEmployeeDetailsBynic(String nic) throws SQLException;

}
