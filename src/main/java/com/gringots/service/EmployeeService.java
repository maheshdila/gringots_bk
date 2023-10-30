package com.gringots.service;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;

import java.sql.SQLException;

public interface EmployeeService {

    CommonResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) throws SQLException;
    CommonResponseDto login(String nic) throws SQLException;

}
