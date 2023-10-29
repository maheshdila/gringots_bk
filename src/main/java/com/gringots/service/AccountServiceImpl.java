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

    @Override
    public CommonResponseDto deposit(long accnum, double amount) throws SQLException {
        return accountDao.deposit(accnum,amount);
    }

    @Override
    public CommonResponseDto transfer(long toAcc, long fromAcc, double amount) throws SQLException {
        return accountDao.transfer(toAcc,fromAcc,amount);
    }

    @Override
    public CommonResponseDto createFd(long savingAcc, double amount, String accountType) throws SQLException {
        CommonResponseDto responseDto =accountDao.getAccount(savingAcc);
        CustomerAccountResponseDto customerAccountResponseDto = (CustomerAccountResponseDto) responseDto.getResponseObject();
        System.out.println(customerAccountResponseDto.getAccountType());
        if(customerAccountResponseDto.getAccountType().equalsIgnoreCase("saving")){
            return accountDao.createFD(savingAcc,amount,accountType);

        }
        else {
            CommonResponseDto commonResponseDto = new CommonResponseDto();
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Invalid saving account");
            commonResponseDto.setQuerySuccesful(false);
            return commonResponseDto;
        }
    }
}
