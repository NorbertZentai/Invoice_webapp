package com.invoicehandler.webapp.data;

import com.invoicehandler.webapp.models.InvoiceMapper;
import com.invoicehandler.webapp.models.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Primary
public class InvoiceDataService implements InvoiceDataInterface{

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public InvoiceModel getById(int id) {
        List<InvoiceModel> result = jdbcTemplate.query("SELECT * FROM INVOICE WHERE Id = ?",
                new InvoiceMapper(), id);
        if(!result.isEmpty()){
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<InvoiceModel> getInvoices() {
        return jdbcTemplate.query("SELECT * FROM INVOICE", new InvoiceMapper());
    }

    @Override
    public List<InvoiceModel> searchInvoices(String searchTerm) {

        List<InvoiceModel> invoices = jdbcTemplate.query("SELECT * FROM INVOICE WHERE TITLE LIKE ? OR COMMENT LIKE ?",
                new InvoiceMapper(), "%" + searchTerm + "%", "%" + searchTerm + "%");

        return invoices;
    }

    @Override
    public int addInvoice(InvoiceModel newInvoice) {
        return jdbcTemplate.update("INSERT INTO INVOICE (CUSTOMER_NAME, DATE_OF_INVOICE, DUE_DATE, TITLE, COMMENT, PRICE) VALUES (?,?,?,?,?,?)",
                newInvoice.getCustomerName(),
                newInvoice.getDateOfInvoice(),
                newInvoice.getDueDate(),
                newInvoice.getTitle(),
                newInvoice.getComment(),
                newInvoice.getPrice()
        );
    }

    @Override
    public boolean deleteInvoice(int id) {
        int result = jdbcTemplate.update("DELETE FROM INVOICE WHERE ID=?", id);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public InvoiceModel updateInvoice(int idToUpdate, InvoiceModel updateInvoice) {

        int result = jdbcTemplate.update("UPDATE INVOICE SET Customer_Name = ?, Date_Of_Invoice = ?, Due_Date = ?, Title = ?, Comment = ?, Price = ? WHERE ID = ?",
                updateInvoice.getCustomerName(),
                updateInvoice.getDateOfInvoice(),
                updateInvoice.getDueDate(),
                updateInvoice.getTitle(),
                updateInvoice.getComment(),
                updateInvoice.getPrice(),
                idToUpdate);

         if (result > 0) {
            return updateInvoice;
        } else {
            return null;
        }
    }
}
