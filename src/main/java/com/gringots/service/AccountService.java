package com.gringots.service;

import com.gringots.model.request.CommonResponseDto;
import com.gringots.model.response.CustomerAccountResponseDto;

import java.sql.SQLException;

public interface AccountService {
     CommonResponseDto getAccountbyNum(long accnum);
     CustomerAccountResponseDto getAccount(long accnum) throws SQLException;

}
