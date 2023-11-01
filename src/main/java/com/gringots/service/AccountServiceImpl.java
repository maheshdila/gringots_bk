package com.gringots.service;

import com.gringots.dao.Customer.AccountDao;
import com.gringots.dao.Customer.CustomerDao;
import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.response.CustomerAccountResponseDto;
import com.gringots.model.response.IndividualResponseDto;
import com.gringots.model.response.OrganizationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountDao accountDao;

    @Autowired
    CustomerDao customerDao;

    @Override
    public CommonResponseDto createAccount(AccountRequestDto accountRequestDto) throws SQLException {
        return accountDao.createAccountUsingProcedures(accountRequestDto);
        //return null;

    }

    @Override
    public CommonResponseDto getAccountbyNum(long accnum) throws SQLException {
        CommonResponseDto commonResponseDto= accountDao.getAccount(accnum);
        CustomerAccountResponseDto customerAccountResponseDto = (CustomerAccountResponseDto) commonResponseDto.getResponseObject();

        if (customerAccountResponseDto.getCustomerType().equalsIgnoreCase("individual")){
            IndividualResponseDto individualResponseDto =  customerDao.getIndividualByid(customerAccountResponseDto.getCustomerId());
            customerAccountResponseDto.setFirstName(individualResponseDto.getFirstName());
            customerAccountResponseDto.setLastName(individualResponseDto.getLastName());
            customerAccountResponseDto.setNic(individualResponseDto.getNic());
            customerAccountResponseDto.setDob(individualResponseDto.getDob());
            commonResponseDto.setResponseObject(customerAccountResponseDto);

        }
        else if(customerAccountResponseDto.getCustomerType().equalsIgnoreCase("organization")){
            OrganizationResponseDto organizationResponseDto = customerDao.getOrganizationByid(customerAccountResponseDto.getCustomerId());
            customerAccountResponseDto.setOrgName(organizationResponseDto.getOrgName());
            customerAccountResponseDto.setOrgRegNumber(organizationResponseDto.getOrgRegnum());
            commonResponseDto.setResponseObject(customerAccountResponseDto);
            //return commonResponseDto;
        }
        return commonResponseDto;
    }

    @Override
    public CommonResponseDto deposit(long accnum, double amount) throws SQLException {
        return accountDao.deposit(accnum,amount);
    }

    @Override
    public CommonResponseDto transfer(long toAcc, long fromAcc, double amount) throws SQLException {
        return accountDao.transfer(toAcc,fromAcc,amount);
    }

    @Override
    public CommonResponseDto createFd(long savingAcc, double amount, String accountType) throws SQLException {
        CommonResponseDto responseDto =accountDao.getAccount(savingAcc);
        CustomerAccountResponseDto customerAccountResponseDto = (CustomerAccountResponseDto) responseDto.getResponseObject();
        System.out.println(customerAccountResponseDto.getAccountType());
        if(customerAccountResponseDto.getAccountType().equalsIgnoreCase("saving")){
            return accountDao.createFD(savingAcc,amount,accountType);

        }
        else {
            CommonResponseDto commonResponseDto = new CommonResponseDto();
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Invalid saving account");
            commonResponseDto.setQuerySuccesful(false);
            return commonResponseDto;
        }
    }

    @Override
    public CommonResponseDto cashWithdrawal(long account_id, double withdrawal_amount) throws SQLException {
        Long withdrawals = (Long) accountDao.getWithdrawals(account_id).getResponseObject();
        CommonResponseDto commonResponseDto;
        //return accountDao.cashWithdrawal(account_id, withdrawal_amount );
        if (withdrawals<=5){
            Double balance = (Double) accountDao.getBalance(account_id).getResponseObject();
            if(balance>=withdrawal_amount) {
                commonResponseDto = accountDao.cashWithdrawal(account_id, withdrawal_amount);
            }
            else{
                commonResponseDto = new CommonResponseDto();
                commonResponseDto.setResponseCode("500");
                commonResponseDto.setResponseMessage("Insufficient balance");
            }
        }
        else{
            commonResponseDto = new CommonResponseDto();
            commonResponseDto.setResponseCode("500");
            commonResponseDto.setResponseMessage("Withdrawals limit exceeded");
            commonResponseDto.setQuerySuccesful(false);
        }
        return commonResponseDto;
    }
    @Override
    public CommonResponseDto getAllTransactions(long branchId,long pageNumber) throws SQLException{
        return accountDao.getAllTransactions(branchId,pageNumber);
    }
    //public CommonResponseDto getCustomerAccount(String email) throws SQLException{

        //getAccountbyNum();
        //return accountDao.getCustomerAccount(customerId);

}
