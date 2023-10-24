package com.gringots.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountRequestDto {
    /*IN p_customer_id bigint,
    IN p_branch_id bigint,
    IN p_balance decimal(20, 2),
    IN p_account_type enum ('saving', 'checking'),
    IN p_s_acc_type enum ('children', 'teen', 'adult', 'senior'),
    OUT p_status int)*/
    private int customerId;
    private int branchId;
    private double balance;
    private String accType;
    private String savingAccType;
}
