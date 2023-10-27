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

    public CommonResponseDto createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try {
            commonResponseDto = accountService.createAccount(accountRequestDto);
        } catch (SQLException e) {
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
        }
        return commonResponseDto;
    }
    @RequestMapping(value = "get/{accnum}" , method = RequestMethod.GET)
    public CommonResponseDto getAccount(@PathVariable("accnum") long accnum){
         //return new CommonResponseDto();
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        //return commonResponseDto;
        try {
            commonResponseDto = accountService.getAccountbyNum(accnum);
            System.out.println(commonResponseDto.getResponseCode()+"controller");
            return commonResponseDto;
        } catch (SQLException e) {
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
            return commonResponseDto;
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
