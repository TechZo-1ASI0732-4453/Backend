package com.techzo.cambiazo.emails.services.impl;

import com.techzo.cambiazo.emails.services.IEmailService;
import com.techzo.cambiazo.emails.services.models.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendMail(EmailDto emailDto) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");

            helper.setTo(emailDto.getDestination());
            helper.setSubject(emailDto.getSubject());

            Context context = new Context();
            context.setVariable("name", emailDto.getName());
            context.setVariable("verification_code", emailDto.getCode());
            String contentHtml = templateEngine.process("email", context);

            helper.setText(contentHtml, true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Error al enviar al correo: "+e.getMessage(),e);
        }
    }
}
