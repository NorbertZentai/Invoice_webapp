package com.invoicehandler.webapp.data;

import com.invoicehandler.webapp.models.InvoiceModel;

import java.util.List;

public interface InvoiceDataInterface {
    public InvoiceModel getById(long id);
    public List<InvoiceModel> getInvoices();
    public List<InvoiceModel> searchInvoices(String SearchTerm);
    public int addInvoice(InvoiceModel newInvoice);
    public boolean deleteInvoice(long ID);
    public InvoiceModel updateInvoice(long idToUpdate, InvoiceModel updateInvoice);
}
