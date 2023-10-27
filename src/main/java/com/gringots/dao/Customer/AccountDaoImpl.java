package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    DataSource dataSource;
    public CommonResponseDto createAccountUsingProcedures(AccountRequestDto accountRequestDto) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL insert_account(?,?,?,?,?,?) }");
        stmt.setInt(1,accountRequestDto.getCustomerId());
        stmt.setInt(2,accountRequestDto.getCustomerId());
        stmt.setDouble(3,accountRequestDto.getBalance());
        stmt.setString(4,accountRequestDto.getAccType());
        stmt.setString(5,accountRequestDto.getSavingAccType());
        stmt.executeUpdate();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (stmt.getInt(6)==0){
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Account created successfully");

        }
        else{
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Account creation failed");

        }
        return commonResponseDto;

    }
    public CommonResponseDto getAccount(long accnum) throws SQLException {
        Connection connection = dataSource.getConnection();
        /*String sql = "SELECT account_no, ac.customer_id, branch_id, account_type, balance, cs.email, cs.address, cs.customer_type, cs.phone_number " +
                "FROM account AS ac " +
                "INNER JOIN customer AS cs ON ac.customer_id = cs.customer_id " +
                "WHERE ac.account_no = ?";*/
        String sql = "SELECT * FROM account_customer_view WHERE account_no = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, accnum);

        ResultSet resultSet = stmt.executeQuery();
        CommonResponseDto response = new CommonResponseDto();
        if (resultSet.next()) {
            // Retrieve data from the ResultSet and create a customerAccountResponseDto object
            CustomerAccountResponseDto responseDto = new CustomerAccountResponseDto();
            responseDto.setAccountNumber(resultSet.getString("account_no"));
            responseDto.setCustomerId(resultSet.getString("customer_id"));
            responseDto.setBranch_id(resultSet.getString("branch_id"));
            responseDto.setAccountType(resultSet.getString("account_type"));
            responseDto.setBalance(resultSet.getDouble("balance"));
            responseDto.setEmail(resultSet.getString("email"));
            responseDto.setAddress(resultSet.getString("address"));
            responseDto.setCustomerType(resultSet.getString("customer_type"));
            responseDto.setPhoneNumber(resultSet.getString("phone_number"));

            response.setResponseCode("200");
            response.setQuerySuccesful(true);
            response.setResponseObject(responseDto);
            response.setResponseMessage("Account found");
        } else {
            response.setResponseCode("404");
            response.setQuerySuccesful(false);
            //response.setResponseObject(responseDto);
            response.setResponseMessage("Account not found");
        }
        return response;
    }
}
