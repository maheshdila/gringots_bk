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
}
