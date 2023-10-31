package com.gringots.model.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class FixedDepositResponseDto {
    private long depositId;
    private long savingsAccountId;
    private double amount;
    private String accountType;

}
