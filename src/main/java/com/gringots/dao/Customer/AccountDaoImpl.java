package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class AccountDaoImpl implements AccountDao{
    @Autowired
    DataSource dataSource;
    public void createAccountUsingProcedures(AccountRequestDto accountRequestDto) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL insert_account(?,?,?,?,?,?) }");
        stmt.setInt(1,accountRequestDto.getCustomerId());
        stmt.setInt(2,accountRequestDto.getCustomerId());
        stmt.setDouble(3,accountRequestDto.getBalance());
        stmt.setString(4,accountRequestDto.getAccType());
        stmt.setString(5,accountRequestDto.getSavingAccType());
        stmt.executeUpdate();
    }
    public CustomerAccountResponseDto getAccount(long accnum) throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "SELECT account_no, ac.customer_id, branch_id, account_type, balance, cs.email, cs.address, cs.customer_type, cs.phone_number " +
                "FROM account AS ac " +
                "INNER JOIN customer AS cs ON ac.customer_id = cs.customer_id " +
                "WHERE ac.account_no = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setLong(1, accnum);

        ResultSet resultSet = stmt.executeQuery();

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
            responseDto.setResponseCode("200");

            return responseDto;
        } else {
            CustomerAccountResponseDto customerAccountResponseDto= new CustomerAccountResponseDto();
            customerAccountResponseDto.setResponseCode("404");
            customerAccountResponseDto.setResponseMessage("Account not found");
            // Handle the case when the account does not exist (return null, throw an exception, etc.)
            return customerAccountResponseDto;
        }
    }
}
