package com.gringots.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties
@Getter
@Setter
public class CustomerRequestDto{

    private String firstName;
    private String lastName;
    private String nic;
    private String dob;
    private String email;
    private String address;
    private String phoneNumber;
    private String nicImage;
    private String customerType;
    private String organizationName;
    private String organizationRegNo;
    private String password;
    private String contactPersonName;
}
