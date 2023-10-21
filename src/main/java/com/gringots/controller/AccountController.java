package com.gringots.controller;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
import com.gringots.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/saving" , method = RequestMethod.POST)
    public String createSaving(@RequestBody CustomerRequestDto customerRequestDto){

        return "saving account created";
    }

    @RequestMapping(value = "/fetchDetails" , method = RequestMethod.GET)
    public CommonResponseDto getCustomerDetails(@PathVariable("email") String email){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        if(email != null){
            CustomerRequestDto customerRequestDto = customerService.getCustomerDetailsById(email);
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseObject(customerRequestDto);
        }


        return  null;
    }
}
