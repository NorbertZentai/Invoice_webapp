package com.invoicehandler.webapp.data;

import com.invoicehandler.webapp.models.InvoiceModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceDAO implements InvoiceDataInterface{

    List<InvoiceModel> invoices = new ArrayList<InvoiceModel>();

    public InvoiceDAO() {
        invoices.add(new InvoiceModel(1, "Karoly", "2024-01-01", "2024-05-01", "GTFO", "Bought for pS3", 7000));

    }

    @Override
    public InvoiceModel getById(int id) {
            for(InvoiceModel invoice : invoices){
                if(invoice.getId() == id){
                    return invoice;
                }
            }
        return null;
    }

    @Override
    public List<InvoiceModel> getInvoices() {

        return invoices;
    }

    @Override
    public List<InvoiceModel> searchInvoices(String searchTerm) {
        List<InvoiceModel> items = new ArrayList<InvoiceModel>();

        for (InvoiceModel invoice : invoices) {
            if (invoice.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                items.add(invoice);
            }
        }

        for (InvoiceModel invoice : invoices) {
            if (invoice.getComment().toLowerCase().contains(searchTerm.toLowerCase())) {
                if (!items.contains(invoice)) {
                    items.add(invoice);
                }
            }
        }

        return items;
    }

    @Override
    public int addInvoice(InvoiceModel newInvoice) {
        boolean success = invoices.add(newInvoice);
        if(success){
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean deleteInvoice(int ID) {
        for(InvoiceModel invoice : invoices){
            if(invoice.getId() == ID){
                invoices.remove(invoice);
                return true;
            }
        }

        return false;
    }

    @Override
    public InvoiceModel updateInvoice(int idToUpdate, InvoiceModel updateInvoice) {

        for(int i=0; i < invoices.size(); i++){
            if(invoices.get(i).getId() == idToUpdate){
                invoices.set(i, updateInvoice);
                return invoices.get(i);
            }
        }

        return null;
    }
}
