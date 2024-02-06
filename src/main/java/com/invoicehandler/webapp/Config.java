package com.invoicehandler.webapp;

import com.invoicehandler.webapp.data.InvoiceDataInterface;
import com.invoicehandler.webapp.models.InvoiceModel;
import com.invoicehandler.webapp.services.InvoiceService;
import com.invoicehandler.webapp.services.InvoiceServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Autowired
    DataSource dataSource;

    @Bean(name="invoice_Service")
    @RequestScope
    public InvoiceServiceInterface getInvoiceService(){
        return new InvoiceService();
    }

}
