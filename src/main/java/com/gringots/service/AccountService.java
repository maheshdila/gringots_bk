package com.gringots.service;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.response.CustomerAccountResponseDto;

import java.sql.SQLException;

public interface AccountService {
      CommonResponseDto createAccount(AccountRequestDto accountRequestDto) throws SQLException;

     CommonResponseDto getAccountbyNum(long accnum) throws SQLException;
     //CustomerAccountResponseDto getAccount(long accnum) throws SQLException;
     CommonResponseDto deposit(long accnum, double amount) throws SQLException;

    CommonResponseDto transfer(long toAcc, long fromAcc, double amount) throws SQLException;

    CommonResponseDto createFd(long savingAcc, double amount, String accountType) throws SQLException;

    CommonResponseDto cashWithdrawal(long account_id, double withdrawal_amount) throws SQLException;
    CommonResponseDto getAllTransactions(long branchId,long pageNumber) throws SQLException;
}
