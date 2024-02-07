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
    @NotEmpty(message = "please add a password!")
    private String newPassword;
    @NotEmpty(message = "please add a password!")
    private String reNewPassword;
    private String role;
    private String lastLogin;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rePassword='" + rePassword + '\'' +
                ", role='" + role + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                '}';
    }

    public UserModel() {
    }

    public UserModel(String username, String password, String rePassword) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
    }
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(int id, String username, String password, String role, String lastLogin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.lastLogin = lastLogin;
    }
}
