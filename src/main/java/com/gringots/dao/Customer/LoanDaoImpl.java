package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.LoanRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class LoanDaoImpl implements LoanDao {
    @Autowired
    DataSource dataSource;
    @Override
    public CommonResponseDto createLoan(LoanRequestDto loanRequestDto) throws SQLException {
        Connection connection = dataSource.getConnection();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        PreparedStatement statement;
        if(loanRequestDto.getLoanType().equalsIgnoreCase("online")){
            String sql = "INSERT INTO loan_info(customer_id," +
                    "branch_id," +
                    "saving_account_id, " +
                    "fixed_deposit_id," +
                    "loan_type, " +
                    "settlement_period," +
                    "amount," +
                    "interest_rate, " +
                    "approval_status," +
                    "approved_date," +
                    "completed_installments," +
                    "total_installments) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,loanRequestDto.getCustomerId());
            statement.setLong(2,loanRequestDto.getBranchId());
            statement.setLong(3,loanRequestDto.getSavingsAccountId());
            statement.setLong(4,loanRequestDto.getFixedDepositId());
            statement.setString(5,loanRequestDto.getLoanType());
            statement.setInt(6,loanRequestDto.getSettlementPeriod());
            statement.setDouble(7,loanRequestDto.getAmount());
            statement.setDouble(8,loanRequestDto.getInterestRate());
            statement.setString(9,loanRequestDto.getLoanStatus());
            statement.setDate(10,(Date)loanRequestDto.getApprovedDate());
            statement.setInt(11,loanRequestDto.getCompletedInstallments());
            statement.setInt(12,loanRequestDto.getTotalInstallments());

        }
        else {
            String sql = "INSERT INTO loan_info(customer_id," +
                    "branch_id," +
                    "saving_account_id, " +
                    //"fixed_deposit_id," +
                    "loan_type, " +
                    "settlement_period," +
                    "amount," +
                    "interest_rate, " +
                    "approval_status," +
                    "approved_date," +
                    "completed_installments," +
                    "total_installments) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1,loanRequestDto.getCustomerId());
            statement.setLong(2,loanRequestDto.getBranchId());
            statement.setLong(3,loanRequestDto.getSavingsAccountId());
            //statement.setLong(4,loanRequestDto.getFixedDepositId());
            statement.setString(4,loanRequestDto.getLoanType());
            statement.setInt(5,loanRequestDto.getSettlementPeriod());
            statement.setDouble(6,loanRequestDto.getAmount());
            statement.setDouble(7,loanRequestDto.getInterestRate());
            statement.setString(8,loanRequestDto.getLoanStatus());
            statement.setDate(9,(Date)loanRequestDto.getApprovedDate());
            statement.setInt(10,loanRequestDto.getCompletedInstallments());
            statement.setInt(11,loanRequestDto.getTotalInstallments());

        }
        //statement.executeUpdate();
        //ResultSet resultSet = statement.getGeneratedKeys();
        if ( statement.executeUpdate()>0){
            System.out.println("Loan request created successfully");
            commonResponseDto.setQuerySuccesful(true);
          //  commonResponseDto.setResponseObject(resultSet.getLong(1));
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Loan request created successfully");

        }
        else{
            commonResponseDto.setQuerySuccesful(true);
            commonResponseDto.setResponseCode("400");
            commonResponseDto.setResponseMessage("Loan request creation failed");
        }
        //resultSet.close();
        //connection.close();
        return commonResponseDto;
    }
}
