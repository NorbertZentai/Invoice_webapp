package com.invoicehandler.webapp.user.data;

import com.invoicehandler.webapp.models.UserModel;

public interface UserInterface {
    public UserModel searchUser(String searchTerm);
    public UserModel searchExactUser(String searchTerm);
    public boolean changePassword();
}
