package com.invoicehandler.webapp.services;

import com.invoicehandler.webapp.models.InvoiceModel;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface InvoiceServiceInterface {
    public InvoiceModel getById(long id);
    public List<InvoiceModel> getInvoices();
    public List<InvoiceModel> searchInvoices(String SearchTerm);
    public int addInvoice(InvoiceModel newInvoice);
    public boolean deleteInvoice(long ID);
    public InvoiceModel updateInvoice(long idToUpdate, InvoiceModel updateInvoice);
}
