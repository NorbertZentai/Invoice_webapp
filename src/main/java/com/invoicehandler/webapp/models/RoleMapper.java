package com.invoicehandler.webapp.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<RoleModel> {

    @Override
    public RoleModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new RoleModel(
                rs.getInt("Id"),
                rs.getString("Role_Name"),
                rs.getString("Description"));
    }

}
