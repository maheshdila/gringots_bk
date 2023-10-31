package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.request.LoanRequestDto;

import java.sql.SQLException;

public interface LoanDao {
        CommonResponseDto createLoan(LoanRequestDto loanRequestDto) throws SQLException;
}
