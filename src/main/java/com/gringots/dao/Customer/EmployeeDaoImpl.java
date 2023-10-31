package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.EmployeeRequestDto;
import com.gringots.model.response.EmployeeResponseDto;
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

    @Override
    public CommonResponseDto getEmployeeDetailsBynic(String nic) throws SQLException {
        CommonResponseDto commonResponseDto  =new CommonResponseDto();
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();

        Connection connection = dataSource.getConnection();
        String sql = "SELECT * FROM employee WHERE NIC=?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1,nic);
        ResultSet resultSet = stmt.executeQuery();
        if(resultSet.next()){
            employeeResponseDto.setEmployee_id(resultSet.getString("emplooyee_id"));
            employeeResponseDto.setBranch_id(resultSet.getString("branch_id"));
            employeeResponseDto.setFirst_name(resultSet.getString("first_name"));
            employeeResponseDto.setLast_name(resultSet.getString("last_name"));
            employeeResponseDto.setNic(resultSet.getString("nic"));
            employeeResponseDto.setDate_of_birth(resultSet.getDate("date_of_birth"));
            employeeResponseDto.setAddress(resultSet.getString("address"));
            employeeResponseDto.setEmployee_type(resultSet.getString("employee_type"));

            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Employee found");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseObject(employeeResponseDto);
        }
        else{
            commonResponseDto.setResponseCode("404");
            commonResponseDto.setResponseMessage("Employee not found");
            commonResponseDto.setQuerySuccesful(false);
        }

        return commonResponseDto;

    }





}


