package com.gringots.service;

import com.gringots.dao.Customer.AccountDao;
import com.gringots.dao.Customer.LoanDao;
import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.LoanRequestDto;
import com.gringots.model.response.FixedDepositResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;

@Service
public class LoanServiceImpl implements com.gringots.service.LoanService{

    @Autowired
    private LoanDao loanDao;
    @Autowired
    private AccountDao accountDao;
    @Override
    public CommonResponseDto getLoanDetails(long loanId) {
        return null;
    }

    @Override
    public CommonResponseDto getLoanDetailsByCustomerId(long customerId) {
        return null;
    }

    @Override
    public CommonResponseDto createLoan(LoanRequestDto loanRequestDto) throws SQLException {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        try{
        if(loanRequestDto.getLoanType().equalsIgnoreCase("online")){

            FixedDepositResponseDto fixedDepositResponseDto = (FixedDepositResponseDto) accountDao.getFD(loanRequestDto.getFixedDepositId()).getResponseObject();//type casting
            if(fixedDepositResponseDto.getAmount()*0.6>= loanRequestDto.getAmount()
                    && loanRequestDto.getAmount()<=500000.00
            &&  (long) accountDao.findCustomer(fixedDepositResponseDto.getSavingsAccountId()).getResponseObject() ==
                    loanRequestDto.getCustomerId()){

                loanRequestDto.setLoanStatus("approved");
                Date utilDate = new Date();
                // Convert java.util.Date to java.sql.Date
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                loanRequestDto.setApprovedDate(sqlDate);
                commonResponseDto = loanDao.createLoan(loanRequestDto);
                commonResponseDto.setResponseMessage("Loan request created successfully and approved");

            }
            else{
                commonResponseDto.setResponseCode("400");
                commonResponseDto.setResponseMessage("Loan request creation failed, Invalid loan amount or invalid fd account");
                commonResponseDto.setQuerySuccesful(false);
                //loanRequestDto.setLoanStatus("rejected");
                //commonResponseDto = loanDao.createLoan(loanRequestDto);

            }
            //return commonResponseDto;
        }
        else if(loanRequestDto.getLoanType().equalsIgnoreCase("physical"))
        {
            commonResponseDto =  loanDao.createLoan(loanRequestDto);
        }
        else {
            commonResponseDto.setQuerySuccesful(false);
            commonResponseDto.setResponseCode("400");
            commonResponseDto.setResponseMessage("invalid type");
        }
        return commonResponseDto;
    }
        catch(NullPointerException e){
            commonResponseDto.setResponseCode("400");
            commonResponseDto.setResponseMessage(e.getMessage());
        }
        return commonResponseDto;
    }


    @Override
    public CommonResponseDto approveLoan(long loanId) {
        return null;
    }
}
