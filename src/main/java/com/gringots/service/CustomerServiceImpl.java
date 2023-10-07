package com.gringots.service;

import com.gringots.dao.Customer.CustomerDao;
import com.gringots.dao.Customer.IndividualDao;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerDao customerDao;
    @Autowired
    IndividualDao individualDao;
    //

    @Override
    public boolean registerCustomer(CustomerRequestDto customerRequestDto) throws SQLException, UnsupportedEncodingException {
        CommonResponseDto customerCreatedResponse =  null;
        CommonResponseDto customerTypeCreatedResponse = null;

        String firstName = customerRequestDto.getFirstName();
        String lastName  = customerRequestDto.getLastName();
        String nic = customerRequestDto.getNic();
        String dob = customerRequestDto.getDob();
        String email = customerRequestDto.getEmail();
        String address = customerRequestDto.getAddress();
        String phoneNumber = customerRequestDto.getPhoneNumber();
        String nicImage = customerRequestDto.getNicImage();
        String customerType =  customerRequestDto.getCustomerType();
        String organizationName =  customerRequestDto.getContactPersonName();
        String organizationRegNo = customerRequestDto.getOrganizationRegNo();
        String contactPersonName =  customerRequestDto.getContactPersonName();


        customerCreatedResponse = customerDao.createCustomer(customerType, address, phoneNumber, nicImage, email);
        CommonResponseDto individualCreatedResponse =
                individualDao.createIndividual(customerCreatedResponse.getGeneratedKey(),firstName,lastName,nic,dob);
        //Assuming that customer email & customer Type is checked for null though the front-end

/*        //Creating customer record
        if(!customerDao.customerAlreadyExist(customerRequestDto.getEmail())){
            customerCreatedResponse = customerDao.createCustomer(customerType,address,phoneNumber,nicImage,email);

            //Getting the id of created customer from common response
            int recordId = customerCreatedResponse.getGeneratedKey();

            //Creating individual or organization record based on type
            if(customerRequestDto != null){
                switch (customerType.toLowerCase()){
                    case "individual":
                        customerTypeCreatedResponse = customerDao.createIndividual(firstName,lastName,nic,dob,recordId);
                        break;
                    case "organization":
                        customerTypeCreatedResponse = customerDao.createOrganization(organizationName,organizationRegNo,contactPersonName,recordId);

                }

            }
            //Checking if record creation is successful
            if(customerCreatedResponse.isQuerySuccesful() && customerTypeCreatedResponse.isQuerySuccesful()){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }*/

    return customerCreatedResponse.isQuerySuccesful();
    }
}
