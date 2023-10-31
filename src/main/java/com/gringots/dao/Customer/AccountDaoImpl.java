package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import com.gringots.model.response.FixedDepositResponseDto;
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
        stmt.setInt(2,accountRequestDto.getBranchId());
        stmt.setDouble(3,accountRequestDto.getBalance());
        stmt.setString(4,accountRequestDto.getAccType());
        stmt.setString(5,accountRequestDto.getSavingAccType());
        stmt.executeUpdate();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (stmt.getInt(6)==0){
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Account created successfully");
            commonResponseDto.setQuerySuccesful(true);
            //commonResponseDto.setResponseObject();

        }
        else{
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Account creation failed");
            commonResponseDto.setQuerySuccesful(false);

        }
        return commonResponseDto;

    }

    public CommonResponseDto getAccount(long accnum) {
        CommonResponseDto response = new CommonResponseDto();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            String sql = "SELECT account_no, ac.customer_id, branch_id, account_type, balance, cs.email, cs.address, cs.customer_type, cs.phone_number " +
                    "FROM account AS ac " +
                    "INNER JOIN customer AS cs ON ac.customer_id = cs.customer_id " +
                    "WHERE ac.account_no = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setLong(1, accnum);

            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                CustomerAccountResponseDto responseDto = new CustomerAccountResponseDto();
                responseDto.setAccountNumber(resultSet.getLong("account_no"));
                responseDto.setCustomerId(resultSet.getLong("customer_id"));
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
                response.setResponseMessage("Account not found");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception or log it
            response.setResponseCode("500");
            response.setQuerySuccesful(false);
            response.setResponseMessage("Internal Server Error");
        } finally {
            // Close resources in a finally block to ensure they are always closed
            try {
                if (resultSet != null) resultSet.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception or log it
            }
        }

        return response;
    }

    @Override
    public CommonResponseDto deposit(long accnum, double amount) throws SQLException {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL cash_deposit(?,?,?) }");//it does n't maatter whetherr we set a
        // value to the 3rd parameter or not as it is output, it will be set by the procedure
        //make sure to put 3 question marks in the procedure
        stmt.setLong(1,accnum);
        stmt.setDouble(2,amount);
        stmt.registerOutParameter(3,Types.DOUBLE);
        stmt.executeUpdate();
        if(stmt.getDouble(3)==0){
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Deposit successful");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseObject(stmt.getDouble(3));
        }
        else{
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Deposit failed");
            commonResponseDto.setQuerySuccesful(false);
        }

    return commonResponseDto;
    }

    @Override
    public CommonResponseDto transfer(long toAcc, long fromAcc, double amount) throws SQLException {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL transfer_funds(?,?,?,?) }");//it does n't maatter whetherr we set a
        stmt.setLong(1,fromAcc);
        stmt.setLong(2,toAcc);
        stmt.setDouble(3,amount);
        stmt.registerOutParameter(4,Types.INTEGER);
        stmt.executeUpdate();
        if (stmt.getInt(4)==0){
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Transfer successful");
            commonResponseDto.setQuerySuccesful(true);
        }
        else{
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Transfer failed");
            commonResponseDto.setQuerySuccesful(false);
        }
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto createFD(long savingAcc, double amount, String accountType) throws SQLException {
        Connection connection= dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO fixed_deposit (saving_acc_no, amount, account_type) VALUES (?,?,?)");
        statement.setLong(1,savingAcc);
        statement.setDouble(2,amount);
        statement.setString(3,accountType);
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if(statement.executeUpdate()>0){
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseMessage("FD created successfully");
            //commonResponseDto.setResponseObject(statement.getGeneratedKeys());
        }
        else {
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setQuerySuccesful(false);
            commonResponseDto.setResponseMessage("FD creation failed");
        }
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto getFD(long accnum) throws SQLException {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        FixedDepositResponseDto fixedDepositResponseDto = new FixedDepositResponseDto();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM fixed_deposit WHERE deposit_id = ?");
        statement.setLong(1,accnum);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            fixedDepositResponseDto.setDepositId(resultSet.getLong("deposit_id"));
            fixedDepositResponseDto.setSavingsAccountId(resultSet.getLong("saving_acc_no"));
            fixedDepositResponseDto.setAmount(resultSet.getDouble("amount"));
            fixedDepositResponseDto.setAccountType(resultSet.getString("account_type"));
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("FD found");
            commonResponseDto.setResponseObject(fixedDepositResponseDto);

        }
        else{
            commonResponseDto.setQuerySuccesful(false);
            commonResponseDto.setResponseMessage("FD not found");
            commonResponseDto.setResponseCode("404");

        }
        System.out.println(fixedDepositResponseDto.toString());
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto findCustomer(long accnum) throws SQLException {
        CommonResponseDto co = new CommonResponseDto();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT customer_id FROM account WHERE account_no = ?");
        statement.setLong(1,accnum);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            co.setResponseCode("200");
            co.setResponseMessage("Customer found");
            co.setQuerySuccesful(true);
            co.setResponseObject((Long) resultSet.getLong("customer_id"));
        }
        else{
            co.setResponseCode("404");
            co.setResponseMessage("Customer not found");
            co.setQuerySuccesful(false);
        }
        return co;
    }


}
