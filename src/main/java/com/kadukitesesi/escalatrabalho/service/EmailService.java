package com.kadukitesesi.escalatrabalho.service;


import com.kadukitesesi.escalatrabalho.api.model.user.models.Email;
import com.kadukitesesi.escalatrabalho.infraestrutura.exceptions.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    public void enviarEmail(Email email) {

        try {

            String corpo = processarTemplate(email);
        MimeMessage mensagem = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, "UTF-8");

        helper.setFrom(from);
        helper.setTo(email.para());
        helper.setSubject(email.assunto());
        helper.setText(corpo, true);


            javaMailSender.send(mensagem);
        } catch (Exception e) {
            new EmailException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    private String processarTemplate(Email email) {

        try {
            Template template = config.getTemplate(email.mensagem());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, email.variaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar o e-mail " + e.getMessage());
        }
    }

}
