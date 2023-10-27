package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.response.CustomerAccountResponseDto;

import java.sql.SQLException;

//@Repository
public interface AccountDao {
     public void createAccountUsingProcedures(AccountRequestDto accountRequestDto) throws SQLException;
     CustomerAccountResponseDto getAccount(long accnum) throws SQLException;
}
