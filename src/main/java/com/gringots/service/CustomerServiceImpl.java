package com.gringots.service;

import com.gringots.dao.Customer.*;
import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerDao customerDao;
    @Autowired
    IndividualDao individualDao;
    @Autowired
     AccountDao accountDao;


    @Override
    public boolean registerCustomer(CustomerRequestDto customerRequestDto) throws SQLException, UnsupportedEncodingException, ParseException {


        CommonResponseDto customerCreatedResponse =  new CommonResponseDto();

        boolean ExistingCustomer = customerDao.customerAlreadyExist(customerRequestDto.getEmail()).isQuerySuccesful();
        if (!ExistingCustomer){
            customerDao.createUsingProcedures(customerRequestDto);
        }


        return customerCreatedResponse.isQuerySuccesful();
    }

    @Override
    public CustomerRequestDto getCustomerDetailsById(String email) {
        customerDao.customerAlreadyExist(email);
        return null;
    }

    @Override
    public CommonResponseDto createAccount(AccountRequestDto accountRequestDto) throws SQLException {
        //AccountDao accountDao = (AccountDao) new AccountDaoImpl();
        accountDao.createAccountUsingProcedures(accountRequestDto);
        return null;
    }
}
