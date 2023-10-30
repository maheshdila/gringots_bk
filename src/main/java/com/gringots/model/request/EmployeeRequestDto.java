package com.gringots.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeRequestDto {
    private int branch_id;
    private String first_name;
    private String last_name;
    private String nic;
    /*private Date date;   */
    private String date;
    private String address;

    private String  sth;
    private String pw_hash;


}
