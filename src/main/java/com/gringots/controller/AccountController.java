package com.gringots.controller;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import com.gringots.service.AccountService;
import com.gringots.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/create" , method = RequestMethod.POST)

    public AccountRequestDto createSaving(@RequestBody AccountRequestDto accountRequestDto) throws SQLException {
        customerService.createAccount(accountRequestDto);
        System.out.println(accountRequestDto.toString());
        return accountRequestDto;
    }
    @RequestMapping(value = "get/{accnum}" , method = RequestMethod.GET)
    public CustomerAccountResponseDto getAccount(@PathVariable("accnum") long accnum){
        //CommonResponseDto commonResponseDto = new CommonResponseDto();
        try {
             CustomerAccountResponseDto response=accountService.getAccount(accnum);
             response.setResponseCode("200");
             return response;
        } catch (SQLException e) {
            CustomerAccountResponseDto response = new CustomerAccountResponseDto();
            response.setResponseCode("404");
            return response;
        }

    }

    @RequestMapping(value = "/fetchDetails/{email}" , method = RequestMethod.GET)
    public CommonResponseDto getCustomerDetails(@PathVariable("email") String email){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if(email != null){
            CustomerRequestDto customerRequestDto = customerService.getCustomerDetailsById(email);
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseObject(customerRequestDto);
        }


        return  commonResponseDto;
    }
}
