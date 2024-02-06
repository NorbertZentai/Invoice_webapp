package com.invoicehandler.webapp.controllers;

import com.invoicehandler.webapp.models.InvoiceModel;
import com.invoicehandler.webapp.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceRestController {
    InvoiceService invoiceService;

    @Autowired
    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/{id}")
    public InvoiceModel getSingleInvoice(@PathVariable(name="id") Long id){
        return invoiceService.getById(id);
    }

    @GetMapping("/list")
    public List<InvoiceModel> showInvoices() {
        return invoiceService.getInvoices();
    }

    @GetMapping("/search/{Term}")
    public List<InvoiceModel> searchInvoices(@PathVariable(name="Term") String searchTerm) {
        return invoiceService.searchInvoices(searchTerm);
    }

    @PostMapping("/add")
    public int addInvoice(@RequestBody InvoiceModel newInvoice) {
        return invoiceService.addInvoice(newInvoice);
    }

    @GetMapping("/delete/{id}")
    public boolean deleteInvoice(@PathVariable(name="id") Long id){

        return invoiceService.deleteInvoice(id);
    }

    @PutMapping("/update/{id}")
    public InvoiceModel deleteInvoice(@RequestBody InvoiceModel updateModel, @PathVariable(name="id") Long id){
        return invoiceService.updateInvoice(id, updateModel);
    }

}