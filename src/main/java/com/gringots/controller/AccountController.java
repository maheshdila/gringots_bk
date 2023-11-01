package com.gringots.controller;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.CustomerRequestDto;
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
            //System.out.println(commonResponseDto.getResponseCode()+"controller");
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
            CustomerRequestDto customerRequestDto = customerService.isCustomerExists(email);
            commonResponseDto.setResponseCode("200");
            commonResponseDto.setResponseObject(customerRequestDto);
        }


        return  commonResponseDto;
    }

    @RequestMapping(value = "/deposit/" ,params = {"accnum","amount"}, method = RequestMethod.POST)
    public CommonResponseDto deposit(@RequestParam long accnum, @RequestParam double amount) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try {
            commonResponseDto = accountService.deposit(accnum,amount);
        } catch (SQLException e) {
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
        }
        return commonResponseDto;
    }
    @RequestMapping(value="/transfer/",params = {"toAcc","fromAcc","amount"}, method = RequestMethod.POST)
    public CommonResponseDto transfer(@RequestParam long toAcc,
                                      @RequestParam long fromAcc,
                                      @RequestParam double amount){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try{
            commonResponseDto = accountService.transfer(toAcc,fromAcc,amount);
        } catch (SQLException e) {
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
        }
        return commonResponseDto;
    }

    @RequestMapping(value="/createFd/", params = {"savingAcc","amount","account_type"}, method = RequestMethod.POST)
    public CommonResponseDto createFd(
            @RequestParam long savingAcc,
            @RequestParam double amount,
            @RequestParam String account_type){
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try{
            commonResponseDto = accountService.createFd(savingAcc,amount,account_type);
        } catch (SQLException e) {
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
        }
        return commonResponseDto;
    }

    @RequestMapping(value = "/cashwithrawals/" ,params = {"account_id","withdrawal_amount"}, method = RequestMethod.POST)
    public CommonResponseDto cashWithdrawal(@RequestParam long account_id, @RequestParam double withdrawal_amount) {
        CommonResponseDto commonResponseDto = new CommonResponseDto();

        try {
            commonResponseDto = accountService.cashWithdrawal(account_id,withdrawal_amount);
        } catch (SQLException e){
            //System.out.println("um ghere");
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
        }
        return commonResponseDto;
    }
    @RequestMapping(value = "/getAllTransactions/", params = {"branchId","pageNumber"}, method = RequestMethod.GET)
    public CommonResponseDto getAllTransactions(@RequestParam long branchId, @RequestParam long pageNumber) {
        //System.out.println("controller");
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try {
            commonResponseDto = accountService.getAllTransactions(branchId,pageNumber);
        } catch (SQLException e) {
            commonResponseDto.setResponseCode(e.getErrorCode()+"");
            commonResponseDto.setResponseMessage(e.getMessage());
        }
        return commonResponseDto;
    }

}
