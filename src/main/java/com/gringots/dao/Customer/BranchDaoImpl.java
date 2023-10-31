package com.gringots.dao.Customer;

import com.gringots.model.request.branchRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class BranchDaoImpl implements BranchDao{
    @Autowired
    DataSource dataSource;
    @Override
    public void createBranch(branchRequestDto branchrequestDto) throws SQLException {
        Connection connection = dataSource.getConnection();
        CallableStatement stmt = connection.prepareCall("{CALL insert_branch(?,?,?,?,?,?) }");
        stmt.setString(1,branchrequestDto.getBranch_name());
        stmt.setString(2,branchrequestDto.getAddress());
        stmt.setLong(3,branchrequestDto.getTelephone());
        stmt.setString(4,branchrequestDto.getManager());
        stmt.setString(5,branchrequestDto.getOpening_hours());

        stmt.executeUpdate();

    }
}
