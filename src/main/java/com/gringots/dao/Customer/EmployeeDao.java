package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;
import com.gringots.model.response.EmployeeResponseDto;

import java.sql.SQLException;

public interface EmployeeDao {
    CommonResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) throws SQLException;

    public CommonResponseDto login(String nic) throws SQLException;

    EmployeeResponseDto getEmployeeDetailsBynic(String nic) throws SQLException;
}
