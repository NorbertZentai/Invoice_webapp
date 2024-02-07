package com.invoicehandler.webapp.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceModel {
    private int id;
    @NotEmpty(message = "Name is required!")
    private String customerName;
    @NotEmpty(message = "Date of Invoice is required!")
    private String dateOfInvoice;
    @NotEmpty(message = "Due date is required!")
    private String dueDate;
    @NotEmpty(message = "Title is required!")
    private String title;
    private String comment;
    @NotNull(message = "Price is required!")
    private int price;

    public InvoiceModel() {
    }

    public InvoiceModel(int id, String customerName, String dateOfInvoice, String dueDate, String title, String comment, int price) {
        this.id = id;
        this.customerName = customerName;
        this.dateOfInvoice = dateOfInvoice;
        this.dueDate = dueDate;
        this.title = title;
        this.comment = comment;
        this.price = price;
    }

    public InvoiceModel(String customerName, String dateOfInvoice, String dueDate, String title, String comment, int price) {
        this.customerName = customerName;
        this.dateOfInvoice = dateOfInvoice;
        this.dueDate = dueDate;
        this.title = title;
        this.comment = comment;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", dateOfInvoice=" + dateOfInvoice +
                ", dueDate=" + dueDate +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", price=" + price +
                '}';
    }
}
