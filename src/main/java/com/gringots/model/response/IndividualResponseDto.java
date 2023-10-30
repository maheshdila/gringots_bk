package com.gringots.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class IndividualResponseDto {
    private String firstName;
    private String lastName;
    private String nic;
    private Date dob;

}
