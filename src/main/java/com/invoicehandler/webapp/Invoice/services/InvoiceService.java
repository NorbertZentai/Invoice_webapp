package com.invoicehandler.webapp.Invoice.services;

import com.invoicehandler.webapp.Invoice.data.DataInterface;
import com.invoicehandler.webapp.models.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService implements ServiceInterface<InvoiceModel> {

    @Autowired
    DataInterface<InvoiceModel> invoiceDAO;

    @Override
    public InvoiceModel getById(int id) {
        return invoiceDAO.getById(id);
    }

    @Override
    public List<InvoiceModel> getItem() {
        return invoiceDAO.getItem();
    }

    @Override
    public List<InvoiceModel> searchItem(String searchTerm) {
        return invoiceDAO.searchItem(searchTerm);
    }

    @Override
    public int addItem(InvoiceModel newItem) {
        return invoiceDAO.addItem(newItem);
    }

    @Override
    public boolean deleteItem(int id) {
        return invoiceDAO.deleteItem(id);
    }

    @Override
    public InvoiceModel updateItem(int idToUpdate, InvoiceModel updateItem) {
        return invoiceDAO.updateItem(idToUpdate, updateItem);
    }
}
