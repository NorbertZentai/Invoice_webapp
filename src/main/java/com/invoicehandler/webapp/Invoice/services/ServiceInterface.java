package com.invoicehandler.webapp.Invoice.services;

import java.util.List;

public interface ServiceInterface<T> {
    public T getById(int id);

    public List<T> getItem();

    public List<T> searchItem(String SearchTerm);

    public int addItem(T newItem);

    public boolean deleteItem(int ID);

    public T updateItem(int idToUpdate, T updateItem);
}
