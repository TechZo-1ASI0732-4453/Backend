package com.techzo.cambiazo.emails.controllers;

import com.techzo.cambiazo.emails.services.IEmailService;
import com.techzo.cambiazo.emails.services.models.EmailDto;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/send-email")
public class EmailController {
    @Autowired
    IEmailService emailService;

    @PostMapping
    private ResponseEntity<String>sendEmail(@RequestBody EmailDto emailDto) throws MessagingException {
        emailService.sendMail(emailDto);
        return new ResponseEntity<>("Email enviado correctamente", HttpStatus.OK);
    }
}
