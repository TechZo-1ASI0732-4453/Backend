package com.techzo.cambiazo.iam.application.internal.outboundservices.captcha;

import com.techzo.cambiazo.iam.application.internal.outboundservices.captcha.model.RecaptchaResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaQueryServiceImpl implements RecaptchaQueryService{

    private static final String GOOGLE_RECAPTCHA_API_URL = "https://www.google.com/recaptcha/api/siteverify";
    private final String RECAPTCHA_SECRET_KEY = "6LdczTcrAAAAABmFrnW9DTL_N6fems3tg41Xwgp4";

    @Override
    public boolean validateRecaptcha(String captcha) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> request = new LinkedMultiValueMap<>();
        request.add("secret", RECAPTCHA_SECRET_KEY);
        request.add("response", captcha);

        RecaptchaResponse apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_API_URL, request, RecaptchaResponse.class);

        return apiResponse != null && Boolean.TRUE.equals(apiResponse.getSuccess());
    }
}
