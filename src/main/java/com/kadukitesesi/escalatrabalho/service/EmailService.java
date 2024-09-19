package com.kadukitesesi.escalatrabalho.service;


import com.kadukitesesi.escalatrabalho.api.model.user.models.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviarEmail(Email email) {
        var mensagem = new SimpleMailMessage();

        mensagem.setFrom("carlos@gmail.com");
        mensagem.setTo(email.para());
        mensagem.setSubject(email.assunto());
        mensagem.setText(email.mensagem());

        javaMailSender.send(mensagem);
    }

}
