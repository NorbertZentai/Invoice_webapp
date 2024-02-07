package com.invoicehandler.webapp.models;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchModel {
    @NotEmpty(message = "Search term is required!")
    String searchTerm;

    public SearchModel() {}

    public SearchModel(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
