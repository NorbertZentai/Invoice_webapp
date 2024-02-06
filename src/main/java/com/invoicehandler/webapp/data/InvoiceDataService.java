package com.invoicehandler.webapp.data;

import com.invoicehandler.webapp.models.InvoiceMapper;
import com.invoicehandler.webapp.models.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class InvoiceDataService implements InvoiceDataInterface{

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public InvoiceModel getById(long id) {
        return null;
    }

    @Override
    public List<InvoiceModel> getInvoices() {
        return jdbcTemplate.query("SELECT * FROM INVOICE", new InvoiceMapper());
    }

    @Override
    public List<InvoiceModel> searchInvoices(String SearchTerm) {
        return null;
    }

    @Override
    public int addInvoice(InvoiceModel newInvoice) {
        return 0;
    }

    @Override
    public boolean deleteInvoice(long ID) {
        return false;
    }

    @Override
    public InvoiceModel updateInvoice(long idToUpdate, InvoiceModel updateInvoice) {
        return null;
    }
}
