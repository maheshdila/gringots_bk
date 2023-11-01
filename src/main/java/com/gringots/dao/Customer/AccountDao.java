package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;

import java.sql.SQLException;

//@Repository
public interface AccountDao {
     public CommonResponseDto createAccountUsingProcedures(AccountRequestDto accountRequestDto) throws SQLException;
     CommonResponseDto getAccount(long accnum) throws SQLException;

     CommonResponseDto deposit(long accnum,double amount) throws SQLException;


     CommonResponseDto transfer(long toAcc, long fromAcc, double amount) throws SQLException;

    CommonResponseDto createFD(long savingAcc, double amount, String accountType) throws SQLException;
    
    CommonResponseDto cashWithdrawal(long account_id, double withdrawal_amount) throws SQLException;
    
    CommonResponseDto getFD(long accnum) throws SQLException;
    
    CommonResponseDto findCustomer(long accnum) throws SQLException;

    public CommonResponseDto getWithdrawals(Long accNum) throws SQLException;
    CommonResponseDto getBalance(long accNum) throws SQLException;

    public CommonResponseDto getAllTransactions(long branchId,long pageNumber) throws SQLException;

}
