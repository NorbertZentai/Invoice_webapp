package com.invoicehandler.webapp.user.services;

import com.invoicehandler.webapp.invoice.services.ServiceInterface;
import com.invoicehandler.webapp.models.UserModel;
import com.invoicehandler.webapp.user.data.UserDataService;
import com.invoicehandler.webapp.user.data.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ServiceInterface<UserModel>, UserInterface {

    @Autowired
    UserDataService userDAO;

    @Override
    public UserModel getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public List<UserModel> getItems() {
        return userDAO.getItems();
    }

    @Override
    public List<UserModel> searchItem(String searchTerm) {
        return userDAO.searchItem(searchTerm);
    }

    public UserModel searchUser(String searchTerm){
        return userDAO.searchUser(searchTerm);
    }

    @Override
    public UserModel searchExactUser(String searchTerm) {
        return userDAO.searchExactUser(searchTerm);
    }

    @Override
    public boolean changePassword() {
        return false;
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