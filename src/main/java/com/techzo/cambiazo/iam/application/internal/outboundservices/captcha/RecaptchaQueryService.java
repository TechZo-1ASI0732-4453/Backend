package com.techzo.cambiazo.iam.application.internal.outboundservices.captcha;

public interface RecaptchaQueryService {
    boolean validateRecaptcha(String captcha);
}
