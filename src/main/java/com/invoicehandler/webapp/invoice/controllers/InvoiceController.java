package com.invoicehandler.webapp.invoice.controllers;

import com.invoicehandler.webapp.invoice.services.ServiceInterface;
import com.invoicehandler.webapp.models.InvoiceModel;
import com.invoicehandler.webapp.models.SearchModel;
import com.invoicehandler.webapp.invoice.services.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
    ServiceInterface<InvoiceModel> invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public String showAllInvoices(Model model){

        List<InvoiceModel> invoices = invoiceService.getItems();

        model.addAttribute("title", "Invoices");
        model.addAttribute("invoices", invoices);

        return "invoice.html";
    }

    @GetMapping("/item")
    public String showInvoice(Model model) {
        InvoiceModel invoice = new InvoiceModel();

        model.addAttribute("title", "Invoice");
        model.addAttribute("invoice", new InvoiceModel());

        return "invoiceitem.html";
    }

    @GetMapping("/create")
    public String showCreateInvoice(Model model) {

        model.addAttribute("title", "Create Invoice");
        model.addAttribute("invoiceModel", new InvoiceModel());

        return "createinvoice";
    }

    @PostMapping("/addNewInvoice")
    public String processInvoice(@Valid InvoiceModel newInvoice, Model model) {

        newInvoice.setId(0);

        invoiceService.addItem(newInvoice);
        List<InvoiceModel> invoices = invoiceService.getItems();

        model.addAttribute("mainTitle", "Item added successfully!");
        model.addAttribute("invoices", invoices);

        return "invoice";
    }

    @GetMapping("/searchForm")
    public String showSearch(Model model) {

        model.addAttribute("title", "Search results");
        model.addAttribute("searchModel", new SearchModel());

        return "searchForm";
    }

    @PostMapping("/search")
    public String search(@Valid SearchModel searchModel, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("searchModel", searchModel);
            return "searchForm";
        }


        List<InvoiceModel> invoices = invoiceService.searchItem(searchModel.getSearchTerm());

        model.addAttribute("title", "Search Result");
        model.addAttribute("mainTitle", "Here are the invoices that we found!");
        model.addAttribute("invoices", invoices);
        return "invoice";
    }

    @PostMapping("/editForm")
    public String editForm(InvoiceModel invoiceModel, Model model){
        InvoiceModel item = invoiceService.getById(invoiceModel.getId());

        model.addAttribute("title", "Edit Invoice");
        model.addAttribute("invoiceModel", item);

        return "editForm";
    }

    @PostMapping("/updateInvoice")
    public String updateInvoice(@Valid InvoiceModel invoiceModel, BindingResult bindingResult, Model model){

        invoiceService.updateItem(invoiceModel.getId(), invoiceModel);

        List<InvoiceModel> invoices = invoiceService.getItems();

        model.addAttribute("title", "Updated item");
        model.addAttribute("mainTitle", "Successfully updated the item with the ID = " + invoiceModel.getId() + "!");
        model.addAttribute("invoices", invoices);

        return "invoice";
    }

    @PostMapping("/delete")
    public String deleteInvoice(@Valid InvoiceModel invoiceModel, Model model){

        InvoiceModel item = invoiceService.getById(invoiceModel.getId());

        invoiceService.deleteItem(invoiceModel.getId());
        List<InvoiceModel> invoices = invoiceService.getItems();

        model.addAttribute("title", "Deleted item");
        model.addAttribute("mainTitle", "Successfully deleted the Item titled " + item.getTitle() + "!");
        model.addAttribute("invoices", invoices);

        return "invoice";
    }


}