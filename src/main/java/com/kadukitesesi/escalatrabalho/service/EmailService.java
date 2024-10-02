package com.kadukitesesi.escalatrabalho.service;


import com.kadukitesesi.escalatrabalho.api.model.user.models.Email;
import com.kadukitesesi.escalatrabalho.infraestrutura.exceptions.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmail(Email email) {

        try {
        var mensagem = new SimpleMailMessage();

        mensagem.setFrom(from);
        mensagem.setTo(email.para());
        mensagem.setSubject(email.assunto());
        mensagem.setText(email.mensagem());


            javaMailSender.send(mensagem);
        } catch (Exception e) {
            new EmailException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

}
