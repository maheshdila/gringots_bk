package com.gringots.service;

import com.gringots.model.request.branchRequestDto;

import java.sql.SQLException;

public interface BranchService {
    void createBranch(branchRequestDto branchrequestDto ) throws SQLException;

}
