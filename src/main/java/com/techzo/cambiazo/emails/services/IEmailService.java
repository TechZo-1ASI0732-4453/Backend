package com.techzo.cambiazo.emails.services;

import com.techzo.cambiazo.emails.services.models.EmailDto;
import jakarta.mail.MessagingException;

public interface IEmailService {
    public void sendMail(EmailDto emailDto) throws MessagingException;
}
