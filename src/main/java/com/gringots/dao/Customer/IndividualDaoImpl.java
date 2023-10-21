package com.gringots.dao.Customer;

import com.gringots.model.request.CommonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class IndividualDaoImpl implements IndividualDao {

    @Autowired
    DataSource dataSource;
    @Override
    public CommonResponseDto createIndividual(long id, String firstName, String lastName, String nic, String dob) throws SQLException  {
        CommonResponseDto commonResponseDto = new CommonResponseDto();
        Connection connection =dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into individual (individual_id,first_name,last_name,nic,dob)" + " values (?,?,?,?,?)");

        preparedStatement.setLong(1, id);
        preparedStatement.setString(2, firstName);
        preparedStatement.setString(3, lastName);
        preparedStatement.setString(4, nic);
        preparedStatement.setDate(5, Date.valueOf(dob));

        preparedStatement.executeUpdate();
        

        return null;
    }
}
