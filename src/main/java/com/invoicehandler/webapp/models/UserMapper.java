package com.invoicehandler.webapp.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserModel> {

    @Override
    public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new UserModel(
                rs.getInt("Id"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("Role"),
                rs.getString("Last_Login"));

    }
}
