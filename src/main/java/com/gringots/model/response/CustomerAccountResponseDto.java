package com.gringots.model.response;


import lombok.Data;

import java.util.Date;

//@Getter
//@Setter
@Data
public class CustomerAccountResponseDto {
    private long accountNumber;
    private long customerId;
    private String branch_id;
    private String accountType;
    private Double balance;
    private String email;
    private String address;
    private String customerType;
    private String phoneNumber;

    private String firstName;
    private String lastName;
    private String nic;
    private Date dob;
    private String orgName;
    private String orgRegNumber;

}
