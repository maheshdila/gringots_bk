package com.gringots.service;

import com.gringots.dao.Customer.BranchDao;
import com.gringots.model.request.branchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class BranchServiceImpl implements BranchService{
    @Autowired
    BranchDao branchDao;

    @Override
    public void createBranch(branchRequestDto branchrequestDto) throws SQLException {
        branchDao.createBranch(branchrequestDto);
    }
}
