package com.invoicehandler.webapp.data;

import com.invoicehandler.webapp.models.InvoiceModel;

import java.util.List;

public interface InvoiceDataInterface {
    public InvoiceModel getById(int id);
    public List<InvoiceModel> getInvoices();
    public List<InvoiceModel> searchInvoices(String searchTerm);
    public int addInvoice(InvoiceModel newInvoice);
    public boolean deleteInvoice(int id);
    public InvoiceModel updateInvoice(int idToUpdate, InvoiceModel updateInvoice);
}
