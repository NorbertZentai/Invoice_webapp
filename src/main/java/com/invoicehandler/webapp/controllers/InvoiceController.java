package com.invoicehandler.webapp.controllers;

import com.invoicehandler.webapp.models.InvoiceModel;
import com.invoicehandler.webapp.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String showInvoice(Model model){
        List<InvoiceModel> invoice = new ArrayList<InvoiceModel>();

        invoice.add(new InvoiceModel(1, "Karoly", "2024-01-01", "2024-05-01", "GTFO", "Bought for pS3", 7000));

        model.addAttribute("title", "Invoice");
        model.addAttribute("invoice", invoice);

        return "invoice";
    }

    @GetMapping("/list")
    public String showInvoices(Model model) {
        List<InvoiceModel> invoices = invoiceService.getInvoices();
        model.addAttribute("title", "Invoice List");
        model.addAttribute("invoices", invoices);
        return "invoicelist";
    }

    @GetMapping("/JSON")
    @ResponseBody
    public List<InvoiceModel> showInvoicesJSON(Model model) {
        return null;
    }

    @GetMapping("/create")
    public String showcreateInvoice(Model model){

        model.addAttribute("title", "Create Invoice");
        model.addAttribute("invoiceModel", invoiceService);

        return "createinvoice";
    }

    @PostMapping("/processInvoice")
    public String processInvoice(@Valid InvoiceModel invoiceModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("invoiceModel", invoiceModel);
            return "invoice";
        }

        model.addAttribute("invoiceModel",invoiceModel);

        return "invoiceResult";
    }
}