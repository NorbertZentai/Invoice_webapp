package com.invoicehandler.webapp.invoice.data;

import java.util.List;

public interface DataInterface<T> {
    public T getById(int id);

    public List<T> getItems();

    public List<T> searchItem(String searchTerm);

    public int addItem(T newItem);

    public boolean deleteItem(int id);

    public T updateItem(int idToUpdate, T updateItem);
}
