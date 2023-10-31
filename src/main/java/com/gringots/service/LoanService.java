package com.gringots.service;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.LoanRequestDto;

import java.sql.SQLException;
import java.util.Date;

public interface LoanService {
    CommonResponseDto getLoanDetails(long loanId);
    CommonResponseDto getLoanDetailsByCustomerId(long customerId);
    CommonResponseDto createLoan(LoanRequestDto loanRequestDto) throws SQLException;

    CommonResponseDto approveLoan(long loanId);
}
