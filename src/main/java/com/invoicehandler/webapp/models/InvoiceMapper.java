package com.invoicehandler.webapp.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InvoiceMapper implements RowMapper<InvoiceModel> {

    @Override
    public InvoiceModel mapRow(ResultSet rs, int rowNum) throws SQLException {

        InvoiceModel invoice = new InvoiceModel(rs.getInt("Id"), rs.getString("Customer_Name"), rs.getDate("Date_Of_Invoice").toLocalDate(), rs.getDate("Due_Date").toLocalDate(), rs.getString("Title"), rs.getString("Comment"), rs.getInt("Price"));

        return invoice;
    }
}
