package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;
import com.gringots.model.request.branchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{
    @Autowired
    DataSource dataSource;

    @Override
    public void createEmployee(EmployeeRequestDto employeeRequestDto) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL insert_employee(?,?,?,?,?,?,?,?,?) }");
        stmt.setInt(1,EmployeeRequestDto.g);


    }
}


