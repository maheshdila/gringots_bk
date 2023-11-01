package com.gringots.service;

import com.gringots.dao.Customer.*;
import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import com.gringots.model.response.IndividualResponseDto;
import com.gringots.model.response.OrganizationResponseDto;
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
        //AccountService accountService = new AccountServiceImpl();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto = customerDao.getAccountCustomerbyEmail(email);   ;
        CommonResponseDto responseDto = new CommonResponseDto();
        //commonResponseDto = customerDao.customerAlreadyExist(email);
        //long customerId = (long) commonResponseDto.getResponseObject();
        if (commonResponseDto.isQuerySuccesful()){
            commonResponseDto =  accountDao.getAccount((long) commonResponseDto.getResponseObject());
            //CommonResponseDto commonResponseDto= accountDao.getAccount(accnum);
            CustomerAccountResponseDto customerAccountResponseDto = (CustomerAccountResponseDto) commonResponseDto.getResponseObject();

            if (customerAccountResponseDto.getCustomerType().equalsIgnoreCase("individual")){
                IndividualResponseDto individualResponseDto =  customerDao.getIndividualByid(customerAccountResponseDto.getCustomerId());
                customerAccountResponseDto.setFirstName(individualResponseDto.getFirstName());
                customerAccountResponseDto.setLastName(individualResponseDto.getLastName());
                customerAccountResponseDto.setNic(individualResponseDto.getNic());
                customerAccountResponseDto.setDob(individualResponseDto.getDob());
                commonResponseDto.setResponseObject(customerAccountResponseDto);

            }
            else if(customerAccountResponseDto.getCustomerType().equalsIgnoreCase("organization")){
                OrganizationResponseDto organizationResponseDto = customerDao.getOrganizationByid(customerAccountResponseDto.getCustomerId());
                customerAccountResponseDto.setOrgName(organizationResponseDto.getOrgName());
                customerAccountResponseDto.setOrgRegNumber(organizationResponseDto.getOrgRegnum());
                commonResponseDto.setResponseObject(customerAccountResponseDto);
                //return commonResponseDto;
            }
            if(commonResponseDto.isQuerySuccesful()){
                return commonResponseDto;
            }
            else{
                commonResponseDto.setResponseCode("500");
                commonResponseDto.setResponseMessage("Account not found");
            }
        }

        return commonResponseDto;
        //return customerDao.getbyEmail(email);
    }
    @Override
    public CommonResponseDto createCustomer(CustomerRequestDto customerRequestDto) throws SQLException, UnsupportedEncodingException, ParseException {
        //CustomerDao customerDao = (CustomerDao) new CustomerDaoImpl();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        commonResponseDto = customerDao.customerAlreadyExist(customerRequestDto.getEmail());
        if (commonResponseDto.isQuerySuccesful()){
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Customer already exists");
            return commonResponseDto;
        }
        else{
            commonResponseDto = customerDao.createUsingProcedures(customerRequestDto);
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseMessage("Customer created successfully");
            return commonResponseDto;
        }
    }

}
