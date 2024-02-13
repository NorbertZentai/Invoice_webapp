package com.invoicehandler.webapp.ReCaptcha;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ReCaptchaService {

    private static final String GOOGLE_RECAPTCHA_ENDPOINT =
            "https://www.google.com/recaptcha/api/siteverify";
    private final String RECAPTCHA_SECRET="6LelyHEpAAAAAPa3mMi6ZYq2Gui_A_ausPvWUS2I";
    public boolean validateCaptcha(String captchaResponse){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", RECAPTCHA_SECRET);
        requestMap.add("response", captchaResponse);

        ReCaptcha apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_ENDPOINT, requestMap, ReCaptcha.class);

        if(apiResponse == null) {
            return false;
        }

        return Boolean.TRUE.equals(apiResponse.isSuccess());
    }

}
