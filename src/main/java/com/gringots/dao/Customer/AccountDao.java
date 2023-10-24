package com.gringots.dao.Customer;

import com.gringots.model.request.AccountRequestDto;
import com.gringots.model.request.CommonResponseDto;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

//@Repository
public interface AccountDao {
     public void createAccountUsingProcedures(AccountRequestDto accountRequestDto) throws SQLException;
}
