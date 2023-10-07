package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;

import java.sql.SQLException;

public interface IndividualDao {
   // boolean customerAlreadyExist(String email);

    //CommonResponseDto createCustomer(String customerType, String address, String phoneNumber, String nicImage, String email) throws SQLException, UnsupportedEncodingException;

    CommonResponseDto createIndividual(long id,String firstName, String lastName, String nic, String dob) throws SQLException;

    //CommonResponseDto createOrganization(String organizationName, String organizationRegNo, String contactPersonName,int recordId) throws SQLException;
}
