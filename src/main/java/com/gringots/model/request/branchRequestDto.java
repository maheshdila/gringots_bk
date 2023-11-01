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
public class branchRequestDto {
    private String branch_id;
    private String branch_name;
    private String address;
    private long telephone;
    private String manager;
    private String opening_hours;
}