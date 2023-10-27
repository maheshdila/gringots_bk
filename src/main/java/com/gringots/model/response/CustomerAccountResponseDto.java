package com.gringots.model.response;


import lombok.Data;

//@Getter
//@Setter
@Data
public class CustomerAccountResponseDto {
    private String accountNumber;
    private String customerId;
    private String branch_id;
    private String accountType;
    private Double balance;
    private String email;
    private String address;
    private String customerType;
    private String phoneNumber;

}
