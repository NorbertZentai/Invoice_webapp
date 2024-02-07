package com.invoicehandler.webapp.role.data;

import com.invoicehandler.webapp.invoice.data.DataInterface;
import com.invoicehandler.webapp.models.RoleMapper;
import com.invoicehandler.webapp.models.UserMapper;
import com.invoicehandler.webapp.models.RoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class RoleDataService implements DataInterface<RoleModel> {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public RoleModel getById(int id) {
        List<RoleModel> result = jdbcTemplate.query("SELECT * FROM ROLE WHERE Id = ?",
                new RoleMapper(), id);
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<RoleModel> getItems() {
        return jdbcTemplate.query("SELECT * FROM ROLE", new RoleMapper());
    }

    @Override
    public List<RoleModel> searchItem(String searchTerm) {
        List<RoleModel> roles = jdbcTemplate.query("SELECT * FROM ROLE WHERE Role_Name LIKE ?",
                new RoleMapper(), "%" + searchTerm + "%");

        return roles;
    }

    @Override
    public int addItem(RoleModel newItem) {
        return jdbcTemplate.update("INSERT INTO ROLE (Role_Name, Description) VALUES (?,?)",
                newItem.getRoleName(),
                newItem.getDescription());
    }

    @Override
    public boolean deleteItem(int id) {
        int result = jdbcTemplate.update("DELETE FROM ROLE WHERE ID = ?", id);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RoleModel updateItem(int idToUpdate, RoleModel updateItem) {
        int result = jdbcTemplate.update("UPDATE ROLE SET Role_Name = ?, Description =  ? WHERE ID = ?",
                updateItem.getRoleName(),
                updateItem.getDescription(),
                idToUpdate);

        if (result > 0) {
            return updateItem;
        } else {
            return null;
        }
    }
}

