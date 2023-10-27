package com.gringots.service;

import com.gringots.dao.Customer.AccountDao;
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
    public CommonResponseDto getAccountbyNum(long accnum) {
        return null;
    }

    @Override
    public CustomerAccountResponseDto getAccount(long accnum) throws SQLException {
        return accountDao.getAccount(accnum);
        //return null;
    }
}
