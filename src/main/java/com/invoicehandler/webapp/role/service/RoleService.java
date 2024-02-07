package com.invoicehandler.webapp.role.service;

import com.invoicehandler.webapp.invoice.services.ServiceInterface;
import com.invoicehandler.webapp.models.RoleModel;
import com.invoicehandler.webapp.role.data.RoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements ServiceInterface<RoleModel> {

    @Autowired
    RoleDataService roleDAO;
    @Override
    public RoleModel getById(int id) {
        return roleDAO.getById(id);
    }

    @Override
    public List<RoleModel> getItems() {
        return roleDAO.getItems();
    }

    @Override
    public List<RoleModel> searchItem(String searchTerm) {
        return roleDAO.searchItem(searchTerm);
    }

    @Override
    public int addItem(RoleModel newItem) {
        return roleDAO.addItem(newItem);
    }

    @Override
    public boolean deleteItem(int id) {
        return roleDAO.deleteItem(id);
    }

    @Override
    public RoleModel updateItem(int idToUpdate, RoleModel updateItem) {
        return roleDAO.updateItem(idToUpdate, updateItem);
    }
}
