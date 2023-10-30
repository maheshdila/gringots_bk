package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class EmployeeDaoImpl implements EmployeeDao{
    @Autowired
    DataSource dataSource;

    @Override
    public CommonResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL insert_employee(?,?,?,?,?,?,?,?,?) }");
        stmt.setInt(1,employeeRequestDto.getBranch_id());
        stmt.setString(2,employeeRequestDto.getFirst_name());
        stmt.setString(3,employeeRequestDto.getLast_name());
        stmt.setString(4,employeeRequestDto.getNic());
        stmt.setDate(5,employeeRequestDto.getDate());
        stmt.setString(6,employeeRequestDto.getAddress());
        stmt.setString(7,employeeRequestDto.getEmployee_type());
        stmt.setString(8,employeeRequestDto.getPw_hash());

        stmt.executeUpdate();

        return null;
    }

    @Override
    public CommonResponseDto login(String nic) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement stmt =
                connection.prepareStatement("SELECT pw_hash FROM employee_credentials where nic=?");
        stmt.setString(1,nic);
        ResultSet resultSet = stmt.executeQuery();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if(resultSet.next()){
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("credential found");
            commonResponseDto.setResponseObject(new String(resultSet.getString("pw_hash")));
        }
        else{
            commonResponseDto.setQuerySuccesful(false);
            commonResponseDto.setResponseCode("404");
            commonResponseDto.setResponseMessage("credential not found");
        }

        return commonResponseDto;
    }
}


