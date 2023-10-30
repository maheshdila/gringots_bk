package com.gringots.dao.Customer;

import com.gringots.model.request.EmployeeRequestDto;

import java.sql.SQLException;

public interface EmployeeDao {
    void createEmployee(EmployeeRequestDto employeeRequestDto) throws SQLException;
}
