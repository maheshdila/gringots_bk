package com.gringots.controller;

import com.gringots.dao.Customer.CustomerDao;
import com.gringots.model.request.CustomerRequestDto;
import com.gringots.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerDao customerDao;

    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public String register(@RequestBody
                                 CustomerRequestDto customerRequestDto) throws Exception {

        customerDao.createUsingProcedures();
           boolean customerCreated =  customerService.registerCustomer(customerRequestDto);

           if(customerCreated){
               return "Customer Created Successful";
           }else {
               return "Customer creation Unsuccessful";
           }
    //return "Customer Created Successful";
    }
}
