package com.invoicehandler.webapp.invoice.controllers;

import com.invoicehandler.webapp.invoice.services.ServiceInterface;
import com.invoicehandler.webapp.models.InvoiceModel;
import com.invoicehandler.webapp.models.SearchModel;
import com.invoicehandler.webapp.invoice.services.InvoiceService;
import com.invoicehandler.webapp.models.UserModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public String showAllInvoices(Model model, HttpServletRequest req,
                                  @Nullable @ModelAttribute("mainTitle") String mainTitle,
                                  @Nullable @ModelAttribute("error") String error){
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null){
            model.addAttribute("title", "Login");
            model.addAttribute("userModel", new UserModel());

            return "login";
        }

        List<InvoiceModel> invoices = invoiceService.getItems();

        model.addAttribute("mainTitle", (mainTitle.isEmpty()?null:mainTitle));
        model.addAttribute("error", (error.isEmpty()?null:error));
        model.addAttribute("title", "Invoices");
        model.addAttribute("invoices", invoices);

        return "invoice";
    }

    @GetMapping("/create")
    public String showCreateInvoice(Model model, HttpServletRequest req) {
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null || user.getRole().equals("user")){
            model.addAttribute("title", "Login");
            model.addAttribute("userModel", new UserModel());

            return "login";
        }

        model.addAttribute("title", "Create Invoice");
        model.addAttribute("invoiceModel", new InvoiceModel());

        return "createinvoice";
    }

    @PostMapping("/addNewInvoice")
    public String processInvoice(@Valid InvoiceModel newInvoice, RedirectAttributes redirectAttributes) {
        if((invoiceService.addItem(newInvoice))==0){
            redirectAttributes.addFlashAttribute("error", "There was an error. Try again!");
        } else{
            redirectAttributes.addFlashAttribute("mainTitle", "Successfully added the new invoice titled "+newInvoice.getTitle());
        }

        return "redirect:/invoice";
    }

    @GetMapping("/searchForm")
    public String showSearch(Model model, HttpServletRequest req) {
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null){
            return "redirect:/login";
        }

        model.addAttribute("title", "Search results");
        model.addAttribute("searchModel", new SearchModel());
        model.addAttribute("invoices", new ArrayList<InvoiceModel>());
        model.addAttribute("error", null);

        return "searchForm";
    }

    @PostMapping("/search")
    public String search(@Valid SearchModel searchModel, Model model) {

        List<InvoiceModel> invoices = invoiceService.searchItem(searchModel.getSearchTerm());

        if(invoices.isEmpty()){
            model.addAttribute("error", "There we no results found!");
        }

        model.addAttribute("invoices", invoices);
        return "searchForm";
    }

    @PostMapping("/readForm")
    public String readForm(InvoiceModel invoiceModel, Model model, HttpServletRequest req){
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null){
            model.addAttribute("title", "Login");
            model.addAttribute("userModel", new UserModel());

            return "login";
        }

        InvoiceModel item = invoiceService.getById(invoiceModel.getId());

        model.addAttribute("title", "Edit Invoice");
        model.addAttribute("invoiceModel", item);

        return "readForm";
    }

    @PostMapping("/editForm")
    public String editForm(InvoiceModel invoiceModel, Model model, HttpServletRequest req,
                           RedirectAttributes redirectAttributes){
        UserModel user = (UserModel) req.getSession().getAttribute("userSession");
        if(user == null || user.getRole().equals("user")){
            model.addAttribute("title", "Login");
            model.addAttribute("userModel", new UserModel());

            return "login";
        }

        InvoiceModel item = invoiceService.getById(invoiceModel.getId());

        if(item==null){
            redirectAttributes.addFlashAttribute("error", "Invoice Not Found!");
            return "redirect:invoice";
        }

        model.addAttribute("title", "Edit Invoice");
        model.addAttribute("invoiceModel", item);

        return "editForm";
    }

    @PostMapping("/updateInvoice")
    public String updateInvoice(@Valid InvoiceModel invoiceModel, RedirectAttributes redirectAttributes){

        if((invoiceService.updateItem(invoiceModel.getId(), invoiceModel)) == null){
            redirectAttributes.addFlashAttribute("error", "Something wen wrong. Try again.");
        } else {
            redirectAttributes.addFlashAttribute("mainTitle", "Successfully updated the item with the ID = " + invoiceModel.getId() + "!");
        }

        return "redirect:/invoice";
    }

    @PostMapping("/delete")
    public String deleteInvoice(@Valid InvoiceModel invoiceModel, RedirectAttributes redirectAttributes){

        InvoiceModel item = invoiceService.getById(invoiceModel.getId());
        if(item==null){
            redirectAttributes.addFlashAttribute("error", "Could not delete, Invoice not existing!");
        }else {
            redirectAttributes.addFlashAttribute("mainTitle", "Successfully deleted the Item titled " + item.getTitle() + "!");
        }

        invoiceService.deleteItem(invoiceModel.getId());

        return "redirect:/invoice";
    }


}