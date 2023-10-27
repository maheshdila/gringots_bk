package com.gringots.service;

import com.gringots.dao.Customer.AccountDao;
import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountDao accountDao;

    @Override
    public CommonResponseDto createAccount(AccountRequestDto accountRequestDto) throws SQLException {
        return accountDao.createAccountUsingProcedures(accountRequestDto);
        //return null;

    }

    @Override
    public CommonResponseDto getAccountbyNum(long accnum) throws SQLException {
        return accountDao.getAccount(accnum);
    }

}
