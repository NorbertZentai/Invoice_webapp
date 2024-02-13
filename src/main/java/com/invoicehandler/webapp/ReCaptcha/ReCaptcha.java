package com.invoicehandler.webapp.ReCaptcha;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReCaptcha {
    private boolean success;
    private String challange_ts;
    private String hostname;
}
