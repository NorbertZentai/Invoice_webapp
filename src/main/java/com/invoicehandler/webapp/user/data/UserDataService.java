package com.invoicehandler.webapp.user.data;

import com.invoicehandler.webapp.invoice.data.DataInterface;
import com.invoicehandler.webapp.models.UserMapper;
import com.invoicehandler.webapp.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class UserDataService implements DataInterface<UserModel>, UserInterface {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserModel getById(int id) {
        List<UserModel> result = jdbcTemplate.query("SELECT * FROM USER WHERE Id = ?",
                new UserMapper(), id);
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<UserModel> getItems() {
        return jdbcTemplate.query("SELECT * FROM USER", new UserMapper());
    }

    @Override
    public List<UserModel> searchItem(String searchTerm) {
        List<UserModel> users = jdbcTemplate.query("SELECT * FROM USER WHERE USERNAME LIKE ?",
                new UserMapper(), "%" + searchTerm + "%");

        return users;
    }

    public UserModel searchUser(String searchTerm) {
        List<UserModel> users = jdbcTemplate.query(
                "SELECT * FROM USER WHERE SOUNDEX(USERNAME) = SOUNDEX(?)",
                new UserMapper(),
                searchTerm
        );
        if(users.isEmpty()){
            return null;
        }else {
            return users.get(0);
        }
    }

    @Override
    public UserModel searchExactUser(String searchTerm) {
        List<UserModel> users = jdbcTemplate.query(
                "SELECT * FROM USER WHERE USERNAME = ?",
                new UserMapper(),
                searchTerm
        );

        if(users.isEmpty()){
            return null;
        }else {
            return users.get(0);
        }
    }

    @Override
    public boolean changePassword() {
        return false;
    }

    @Override
    public int addItem(UserModel newItem) {
        return jdbcTemplate.update("INSERT INTO USER (USERNAME, PASSWORD, ROLE, LAST_LOGIN) VALUES (?,?,?,?)",
                newItem.getUsername(),
                newItem.getPassword(),
                newItem.getRole(),
                newItem.getLastLogin());
    }

    @Override
    public boolean deleteItem(int id) {
        int result = jdbcTemplate.update("DELETE FROM USER WHERE ID = ?", id);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserModel updateItem(int idToUpdate, UserModel updateItem) {
        int result = jdbcTemplate.update("UPDATE USER SET USERNAME = ?, PASSWORD = ?, ROLE = ?, LAST_LOGIN = ? WHERE ID = ?",
                updateItem.getUsername(),
                updateItem.getPassword(),
                updateItem.getRole(),
                updateItem.getLastLogin(),
                idToUpdate);

        if (result > 0) {
            return updateItem;
        } else {
            return null;
        }
    }
}
