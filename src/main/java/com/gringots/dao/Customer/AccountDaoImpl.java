package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import com.gringots.model.response.FixedDepositResponseDto;
import com.gringots.model.response.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class AccountDaoImpl implements AccountDao {
    @Autowired
    DataSource dataSource;

    public CommonResponseDto createAccountUsingProcedures(AccountRequestDto accountRequestDto) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL insert_account(?,?,?,?,?,?) }");
        stmt.setInt(1, accountRequestDto.getCustomerId());
        stmt.setInt(2, accountRequestDto.getBranchId());
        stmt.setDouble(3, accountRequestDto.getBalance());
        stmt.setString(4, accountRequestDto.getAccType());
        stmt.setString(5, accountRequestDto.getSavingAccType());
        stmt.executeUpdate();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (stmt.getInt(6) == 0) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Account created successfully");
            commonResponseDto.setQuerySuccesful(true);
            //commonResponseDto.setResponseObject();

        } else {
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
                    "WHERE ac.account_no = ? ";
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
        stmt.setLong(1, accnum);
        stmt.setDouble(2, amount);
        stmt.registerOutParameter(3, Types.DOUBLE);
        stmt.executeUpdate();
        if (stmt.getDouble(3) == 0) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Deposit successful");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseObject(stmt.getDouble(3));
        } else {
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Deposit failed");
            commonResponseDto.setQuerySuccesful(false);
        }
        //resultSet.close();
        connection.close();
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto transfer(long toAcc, long fromAcc, double amount) throws SQLException {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL transfer_funds(?,?,?,?) }");//it does n't maatter whetherr we set a
        stmt.setLong(1, fromAcc);
        stmt.setLong(2, toAcc);
        stmt.setDouble(3, amount);
        stmt.registerOutParameter(4, Types.INTEGER);
        stmt.executeUpdate();
        if (stmt.getInt(4) == 0) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Transfer successful");
            commonResponseDto.setQuerySuccesful(true);
        } else {
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Transfer failed");
            commonResponseDto.setQuerySuccesful(false);
        }
        //resultSet.close();
        connection.close();
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto createFD(long savingAcc, double amount, String accountType) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO fixed_deposit (saving_acc_no, amount, account_type) VALUES (?,?,?)");
        statement.setLong(1, savingAcc);
        statement.setDouble(2, amount);
        statement.setString(3, accountType);
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (statement.executeUpdate() > 0) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseMessage("FD created successfully");
            //commonResponseDto.setResponseObject(statement.getGeneratedKeys());
        } else {
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setQuerySuccesful(false);
            commonResponseDto.setResponseMessage("FD creation failed");
        }
        //resultSet.close();
        connection.close();
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto cashWithdrawal(long account_id, double withdrawal_amount) throws SQLException {
        System.out.println(account_id+ " " + withdrawal_amount);
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL cash_withdraw(?,?,?) }");
        stmt.setLong(1, account_id);
        stmt.setDouble(2, withdrawal_amount);
        stmt.registerOutParameter(3, Types.INTEGER);
        stmt.execute();


        if (stmt.getInt(3) == 0) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Withdrawal is successfull");
            commonResponseDto.setQuerySuccesful(true);

            //commonResponseDto.setResponseObject();

        } else {
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Withdrawal failed");
            commonResponseDto.setQuerySuccesful(false);

        }
        //resultSet.close();
        connection.close();
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto getFD(long accnum) throws SQLException {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        FixedDepositResponseDto fixedDepositResponseDto = new FixedDepositResponseDto();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM fixed_deposit WHERE deposit_id = ?");
        statement.setLong(1, accnum);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            fixedDepositResponseDto.setDepositId(resultSet.getLong("deposit_id"));
            fixedDepositResponseDto.setSavingsAccountId(resultSet.getLong("saving_acc_no"));
            fixedDepositResponseDto.setAmount(resultSet.getDouble("amount"));
            fixedDepositResponseDto.setAccountType(resultSet.getString("account_type"));
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("FD found");
            commonResponseDto.setResponseObject(fixedDepositResponseDto);

        } else {
            commonResponseDto.setQuerySuccesful(false);
            commonResponseDto.setResponseMessage("FD not found");
            commonResponseDto.setResponseCode("404");

        }
        //System.out.println(fixedDepositResponseDto.toString());
        resultSet.close();
        connection.close();
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto findCustomer(long accnum) throws SQLException {
        CommonResponseDto co = new CommonResponseDto();
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT customer_id FROM account WHERE account_no = ?");
        statement.setLong(1, accnum);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            co.setResponseCode("200");
            co.setResponseMessage("Customer found");
            co.setQuerySuccesful(true);
            co.setResponseObject((Long) resultSet.getLong("customer_id"));
        } else {
            co.setResponseCode("404");
            co.setResponseMessage("Customer not found");
            co.setQuerySuccesful(false);
        }
        resultSet.close();
        connection.close();
        return co;

    }
    public CommonResponseDto getWithdrawals(Long accNum) throws SQLException {
        Connection connection= dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT num_of_withdrawals FROM saving_account WHERE saving_acc_no = ?");
        statement.setLong(1, accNum);
        ResultSet resultSet = statement.executeQuery();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (resultSet.next()) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Withdrawal found");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseObject((Long) resultSet.getLong("num_of_withdrawals"));
        } else {
            commonResponseDto.setResponseCode("404");
            commonResponseDto.setResponseMessage("Withdrawal not found");
            commonResponseDto.setQuerySuccesful(false);
        }

        resultSet.close();
        connection.close();
        return commonResponseDto;

    }
    public CommonResponseDto getBalance(long accNum) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT balance FROM account WHERE account_no = ?");
        statement.setLong(1, accNum);
        ResultSet resultSet = statement.executeQuery();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (resultSet.next()) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Balance found");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseObject((Double) resultSet.getDouble("balance"));
        } else {
            commonResponseDto.setResponseCode("404");
            commonResponseDto.setResponseMessage("Balance not found");
            commonResponseDto.setQuerySuccesful(false);
        }
        resultSet.close();
        connection.close();
        return commonResponseDto;
    }
    public CommonResponseDto getAllTransactions(long branchId,long pageNumber) throws SQLException{
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        Connection connection = dataSource.getConnection();
        String sql = "SELECT * FROM total_transactions Where branch_id = ? ORDER BY Timestamp DESC LIMIT ? OFFSET ? ";
        //"SELECT * FROM total_transactions WHERE Timestamp BETWEEN '2023-10-20 00:00:00' AND '2023-11-01 23:59:59' ORDER BY Timestamp DESC LIMIT ? OFFSET ?"
        //connection.prepareStatement(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(2, 25);
        statement.setLong(3, (pageNumber-1)*25);
        statement.setInt(1, (int) branchId);
        ResultSet resultSet = statement.executeQuery();
        TransactionResponseDto[] transactionResponseDtos = new TransactionResponseDto[25];


        while(resultSet.next()) {
            //resultSet.previous();
            TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
            transactionResponseDto.setAccNum(resultSet.getLong("account_id"));
            transactionResponseDto.setAmount(resultSet.getDouble("amount"));
            transactionResponseDto.setTransactionType(resultSet.getString("transaction_type"));
            transactionResponseDto.setTimestamp(resultSet.getTimestamp("timestamp"));
            if (transactionResponseDto.getTimestamp() != null) {
                transactionResponseDtos[resultSet.getRow() - 1] = transactionResponseDto;
            }
        }
        if(transactionResponseDtos.length!=0) {
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Transactions found");
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseObject(transactionResponseDtos);
        }
        else{
            commonResponseDto.setResponseCode("404");
            commonResponseDto.setResponseMessage("Transactions not found");
            commonResponseDto.setQuerySuccesful(false);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return commonResponseDto;
    }



}
