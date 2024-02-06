package com.invoicehandler.webapp.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegistrationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Please add a username!")
    private String username;
    @NotEmpty(message = "Please add a password!")
    private String password;
    @NotEmpty(message = "please add a password!")
    private String rePassword;
    private Date lastLogin;

    @Override
    public String toString() {
        return "RegistrationModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rePassword='" + rePassword + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }

    public RegistrationModel() {
    }

    public RegistrationModel(String username, String password, String rePassword) {
        this.username = username;
        this.password = password;
        this.rePassword = rePassword;
        this.lastLogin = new Date();
    }
}
