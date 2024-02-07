package com.invoicehandler.webapp.Invoice.controllers;

import com.invoicehandler.webapp.models.InvoiceModel;
import com.invoicehandler.webapp.Invoice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceRestController {
    InvoiceService service;

    @Autowired
    public InvoiceRestController(InvoiceService invoiceService) {
        this.service = invoiceService;
    }

    @GetMapping("/{id}")
    public InvoiceModel getSingleInvoice(@PathVariable(name = "id") int id) {
        return service.getById(id);
    }

    @GetMapping("/list")
    public List<InvoiceModel> showInvoices() {
        return service.getItem();
    }

    @GetMapping("/search/{Term}")
    public List<InvoiceModel> searchInvoices(@PathVariable(name = "Term") String searchTerm) {
        return service.searchItem(searchTerm);
    }

    @PostMapping("/add")
    public int addInvoice(@RequestBody InvoiceModel newInvoice) {
        return service.addItem(newInvoice);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteInvoice(@PathVariable(name = "id") int id) {

        return service.deleteItem(id);
    }

    @PutMapping("/update/{id}")
    public InvoiceModel deleteInvoice(@RequestBody InvoiceModel updateModel, @PathVariable(name = "id") int id) {
        return service.updateItem(id, updateModel);
    }

}