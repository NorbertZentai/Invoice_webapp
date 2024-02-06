package com.invoicehandler.webapp.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    private String customerName;
    @NotEmpty
    private String dateOfInvoice;
    @NotEmpty
    private String dueDate;
    @NotEmpty
    private String title;
    @NotEmpty
    private String comment;
    @NotEmpty
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
