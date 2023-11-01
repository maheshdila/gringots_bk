package com.gringots.service;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;

public interface CustomerService {
    boolean registerCustomer(CustomerRequestDto customerRequestDto) throws SQLException, UnsupportedEncodingException, ParseException;

    CustomerRequestDto isCustomerExists(String id);

    CommonResponseDto createAccount(AccountRequestDto accountRequestDto) throws SQLException;

    CommonResponseDto login(String email) throws SQLException;
    CommonResponseDto getcustomerAccountbyEmail(String email) throws SQLException;

    CommonResponseDto createCustomer(CustomerRequestDto customerRequestDto) throws SQLException, UnsupportedEncodingException, ParseException;
}
