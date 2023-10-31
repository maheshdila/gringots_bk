package com.gringots.model.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class EmployeeResponseDto {
    private String employee_id;
    private String branch_id;
    private String first_name;
    private String last_name;
    private String nic;
    private Date date_of_birth;
    private String address;
    private String employee_type;

}
