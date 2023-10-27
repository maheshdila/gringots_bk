package com.gringots.model.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponseDto {

    boolean isQuerySuccesful;
    private String responseCode;
    private String responseMessage;
    private Object responseObject;
}
