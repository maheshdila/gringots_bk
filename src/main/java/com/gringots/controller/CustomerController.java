package com.gringots.controller;

import com.gringots.dao.Customer.CustomerDao;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import com.gringots.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDao customerDao;

    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public CommonResponseDto register(@RequestBody
                                 CustomerRequestDto customerRequestDto)  {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try {
            commonResponseDto = customerService.createCustomer(customerRequestDto);
        }
        catch (Exception e) {
            //commonResponseDto.setResponseCode(e.gete);
            commonResponseDto.setResponseMessage(e.getMessage());
        }

        //customerDao.createUsingProcedures();
           //boolean customerCreated =  customerService.registerCustomer(customerRequestDto);
    return commonResponseDto;
    //return "Customer Created Successful";
    }
   // @GetMapping(params = {"email"})
    @RequestMapping(value="/login/{email}", method = RequestMethod.GET)
    public CommonResponseDto login(@PathVariable String email){
        CommonResponseDto commonResponseDto;
        try {
            return customerService.login(email);
        } catch (SQLException e) {
            commonResponseDto = new CommonResponseDto();
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
            return commonResponseDto;
        }
    }

    @RequestMapping(value="/getcustomer/{email}", method = RequestMethod.GET)
    public CommonResponseDto getcustomer(@PathVariable String email){
        CommonResponseDto commonResponseDto;
        try {
            return customerService.getcustomerAccountbyEmail(email);
        } catch (SQLException e) {
            commonResponseDto = new CommonResponseDto();
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
            return commonResponseDto;
        }
    }
}
