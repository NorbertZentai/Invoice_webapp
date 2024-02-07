package com.invoicehandler.webapp.models;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    @Id
    private int id;
    @NotEmpty(message = "Please add a username!")
    private String username;
    @NotEmpty(message = "Please add a password!")
    private String password;
    @NotEmpty(message = "please add a password!")
    private String rePassword;
    private String lastLogin;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rePassword='" + rePassword + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                '}';
    }

    public UserModel(int id, String username, String password, String role, String lastLogin) {
    }

    public UserModel() {
    }

    public UserModel(String username, String password, String rePassword) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
    }
}
