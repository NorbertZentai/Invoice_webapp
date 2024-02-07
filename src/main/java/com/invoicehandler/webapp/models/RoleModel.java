package com.invoicehandler.webapp.models;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleModel {
    private int id;
    @NotEmpty(message = "Please add a username!")
    private String roleName;
    private String description;

    @Override
    public String toString() {
        return "RoleModel{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public RoleModel() {
    }

    public RoleModel(int id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    public RoleModel(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public RoleModel(String roleName) {
        this.roleName = roleName;
    }
}
