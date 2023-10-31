package com.gringots.model.request;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanRequestDto {


    private long customerId;
    private long branchId;
    private long savingsAccountId;
    private long fixedDepositId;
    private String loanType;
    private int settlementPeriod;
    private Double amount;
    private Double interestRate = 0.2; // Default interest rate is 0.2
    private String loanStatus = "pending"; // Default loan status is pending
    private Date approvedDate;
    private int completedInstallments;
    private int totalInstallments;
}