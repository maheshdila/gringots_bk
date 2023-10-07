package com.gringots.dao;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public interface CustomerDao {

    boolean customerAlreadyExist(String email);

    CommonResponseDto createCustomer(String customerType, String address, String phoneNumber, String nicImage, String email) throws SQLException, UnsupportedEncodingException;

    CommonResponseDto createIndividual(String firstName, String lastName, String nic, String dob,int recordId) throws SQLException;

    CommonResponseDto createOrganization(String organizationName, String organizationRegNo, String contactPersonName,int recordId) throws SQLException;
}
