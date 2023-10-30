package com.gringots.service;

import com.gringots.dao.Customer.EmployeeDao;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public CommonResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) throws SQLException {
        employeeDao.createEmployee(employeeRequestDto);

        return null;
    }
}
