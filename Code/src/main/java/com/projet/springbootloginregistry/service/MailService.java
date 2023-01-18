package com.projet.springbootloginregistry.service;


import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    public void sendMailActivationAccount(String activationUrl,String email){
        //creer une email
        MimeMessage mimeMessage =javaMailSender.createMimeMessage();
        try{
            //
            MimeMessageHelper message =new MimeMessageHelper(mimeMessage,true);
            //

            message.setSubject(("welcome to our website-account activation"));
            message.setFrom(mailUsername);

            message.setTo(email);
            message.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("activationUrl",activationUrl);
            String text =templateEngine.process("activation-account.html",context);

            message.setText(text,true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

}
