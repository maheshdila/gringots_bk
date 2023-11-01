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
    public CustomerRequestDto isCustomerExists(String email) {
        customerDao.customerAlreadyExist(email);
        return null;
    }

    @Override
    public CommonResponseDto createAccount(AccountRequestDto accountRequestDto) throws SQLException {
        //AccountDao accountDao = (AccountDao) new AccountDaoImpl();
        accountDao.createAccountUsingProcedures(accountRequestDto);
        return null;
    }

    @Override
    public CommonResponseDto login(String email) throws SQLException {
        return customerDao.login(email);
    }
    @Override
    public CommonResponseDto getcustomerAccountbyEmail(String email) throws SQLException {
        //CustomerService customerService = new CustomerServiceImpl();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto = customerDao.getAccountCustomerbyEmail(email);   ;
        CommonResponseDto responseDto = new CommonResponseDto();
        //commonResponseDto = customerDao.customerAlreadyExist(email);
        //long customerId = (long) commonResponseDto.getResponseObject();
        if (commonResponseDto.isQuerySuccesful()){
            responseDto =  accountDao.getAccount((long) commonResponseDto.getResponseObject());
            if(responseDto.isQuerySuccesful()){
                return responseDto;
            }
            else{
                responseDto.setResponseCode("500");
                responseDto.setResponseMessage("Account not found");
                return responseDto;
            }
        }

        return commonResponseDto;
        //return customerDao.getbyEmail(email);
    }

}
