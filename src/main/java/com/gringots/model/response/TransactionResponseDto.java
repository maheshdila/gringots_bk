package com.gringots.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TransactionResponseDto {
    private long accNum;
    private String transactionType;
    private java.sql.Timestamp Timestamp;
    private double amount;
}
