package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import com.gringots.model.response.IndividualResponseDto;
import com.gringots.model.response.OrganizationResponseDto;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;

public interface CustomerDao {

    CommonResponseDto customerAlreadyExist(String email);

    CommonResponseDto createCustomer(String customerType, String address, String phoneNumber, String nicImage, String email) throws SQLException, UnsupportedEncodingException;

    CommonResponseDto createIndividual(String firstName, String lastName, String nic, String dob,int recordId) throws SQLException;

    CommonResponseDto createUsingProcedures(CustomerRequestDto customerRequestDto) throws SQLException, UnsupportedEncodingException, ParseException;
    void setAutoCommit(boolean b) throws SQLException;
    void commit() throws SQLException;
    void rollback() throws SQLException;
    //CommonResponseDto createOrganization(String organizationName, String organizationRegNo, String contactPersonName,int recordId) throws SQLException;
    void createUsingProcedures() throws SQLException, UnsupportedEncodingException;

    public CommonResponseDto login(String email) throws SQLException;

    IndividualResponseDto getIndividualByid(long customerId) throws SQLException;
    OrganizationResponseDto getOrganizationByid(long customerId) throws SQLException;

    CommonResponseDto getAccountCustomerbyEmail(String email) throws SQLException;

}
