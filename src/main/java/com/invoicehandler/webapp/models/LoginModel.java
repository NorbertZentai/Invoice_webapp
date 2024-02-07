package com.invoicehandler.webapp.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel {
    @NotEmpty(message = "Username is required!")
    private String username;

    @NotEmpty(message = "Password is required!")
    private String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginModel() {}

    @Override
    public String toString() {
        return "LoginModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
