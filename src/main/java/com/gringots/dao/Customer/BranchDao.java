package com.gringots.dao.Customer;

import com.gringots.model.request.branchRequestDto;

import java.sql.SQLException;

public interface BranchDao {
    void createBranch(branchRequestDto branchrequestDto) throws SQLException;

}
