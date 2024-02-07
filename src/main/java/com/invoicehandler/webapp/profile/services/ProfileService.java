package com.invoicehandler.webapp.profile.services;

import com.invoicehandler.webapp.Invoice.data.DataInterface;
import com.invoicehandler.webapp.Invoice.services.ServiceInterface;
import com.invoicehandler.webapp.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService implements ServiceInterface<UserModel> {

    @Autowired
    DataInterface<UserModel> userDAO;

    @Override
    public UserModel getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public List<UserModel> getItem() {
        return userDAO.getItem();
    }

    @Override
    public List<UserModel> searchItem(String searchTerm) {
        return userDAO.searchItem(searchTerm);
    }

    @Override
    public int addItem(UserModel newItem) {
        return userDAO.addItem(newItem);
    }

    @Override
    public boolean deleteItem(int id) {
        return userDAO.deleteItem(id);
    }

    @Override
    public UserModel updateItem(int idToUpdate, UserModel updateItem) {
        return userDAO.updateItem(idToUpdate, updateItem);
    }
}