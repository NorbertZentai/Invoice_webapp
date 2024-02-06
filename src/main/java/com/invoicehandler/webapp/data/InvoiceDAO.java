package com.invoicehandler.webapp.data;

import com.invoicehandler.webapp.models.InvoiceModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceDAO implements InvoiceDataInterface{

    List<InvoiceModel> invoices = new ArrayList<InvoiceModel>();

    public InvoiceDAO() {
        invoices.add(new InvoiceModel(1, "Karoly", LocalDate.of(2024, 1, 1), LocalDate.of(2024,5, 1), "GTFO", "Bought for pS3", 7000));
        invoices.add(new InvoiceModel(2,"Sandor", LocalDate.of(2024, 1, 2), LocalDate.of(2024, 5, 2), "GTA VI", "Bought for Switch", 300000));
        invoices.add(new InvoiceModel(3,"Lajos", LocalDate.of(2024, 1, 3), LocalDate.of(2024, 5, 3), "LEGO Fortnite", "Bought for psp", 1));
        invoices.add(new InvoiceModel(4,"Eniko", LocalDate.of(2024, 1, 4), LocalDate.of(2024, 5, 4), "Hollow Knight", "Bought as a card game", 1500));
        invoices.add(new InvoiceModel(5,"Marta", LocalDate.of(2024, 1, 5), LocalDate.of(2024, 5, 5), "Kingdom Two Crown", "Bought as a duke", 1500));
        invoices.add(new InvoiceModel(6,"Gizi", LocalDate.of(2024, 1, 6), LocalDate.of(2024, 5, 6), "Rainbow", "Volunteer in the army.", 3000));

    }

    @Override
    public InvoiceModel getById(long id) {
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
    public boolean deleteInvoice(long ID) {
        for(InvoiceModel invoice : invoices){
            if(invoice.getId() == ID){
                invoices.remove(invoice);
                return true;
            }
        }

        return false;
    }

    @Override
    public InvoiceModel updateInvoice(long idToUpdate, InvoiceModel updateInvoice) {

        for(int i=0; i < invoices.size(); i++){
            if(invoices.get(i).getId() == idToUpdate){
                invoices.set(i, updateInvoice);
                return invoices.get(i);
            }
        }

        return null;
    }
}
