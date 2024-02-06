package com.invoicehandler.webapp.services;

import com.invoicehandler.webapp.data.InvoiceDataInterface;
import com.invoicehandler.webapp.models.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService implements InvoiceServiceInterface{

    @Autowired
    InvoiceDataInterface invoiceDAO;

    @Override
    public InvoiceModel getById(int id) {
        return invoiceDAO.getById(id);
    }

    @Override
    public List<InvoiceModel> getInvoices() {
        return invoiceDAO.getInvoices();
    }

    @Override
    public List<InvoiceModel> searchInvoices(String searchTerm) {
        return invoiceDAO.searchInvoices(searchTerm);
    }

    @Override
    public int addInvoice(InvoiceModel newInvoice) {
        return invoiceDAO.addInvoice(newInvoice);
    }

    @Override
    public boolean deleteInvoice(int id) {
        return invoiceDAO.deleteInvoice(id);
    }

    @Override
    public InvoiceModel updateInvoice(int idToUpdate, InvoiceModel updateInvoice) {
        return invoiceDAO.updateInvoice(idToUpdate, updateInvoice);
    }
}
