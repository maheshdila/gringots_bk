package com.gringots.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties
@Getter
@Setter
@NoArgsConstructor
public class CustomerRequestDto{

    private String firstName;
    private String lastName;
    private String branchId;
    private String nic;
    private String address;
    private String initialAmount;
    private String email;
    private String phoneNumber;
    private String dob;
    private String customerType;
    private String organizationName;
    private String organizationRegNo;
    private String accountType;
    private String password;
    private String contactPersonName;
}
