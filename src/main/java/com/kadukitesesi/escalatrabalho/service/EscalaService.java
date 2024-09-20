package com.kadukitesesi.escalatrabalho.service;

import com.kadukitesesi.escalatrabalho.api.model.user.models.Email;
import com.kadukitesesi.escalatrabalho.api.model.user.models.UserModel;
import com.kadukitesesi.escalatrabalho.api.model.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EscalaService {

    private LocalTime entrada;
    private LocalTime saida;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public void criarEscala(String nome, Date dataServico) {
        Optional<UserModel> usuarioBuscado = userRepository.findByUsername(nome);
        if (usuarioBuscado.isPresent()) {
            UserModel usuario = usuarioBuscado.get();
            List<Date> servicos = usuario.getDataServico() != null ? usuario.getDataServico() : new ArrayList<>();
            servicos.add(dataServico);
            usuario.setDataServico(servicos);
            userRepository.save(usuario);
        }
    }

    public String baterPontoEntrada(String username) {
        LocalTime horarioEntrada = LocalTime.of(8, 0);
        entrada = LocalTime.now();
        String mensagem;

        if (entrada.isAfter(horarioEntrada)) {
            long horasExcedentes = Duration.between(horarioEntrada, entrada).toHours();
            mensagem = username + " está atrasado em " + horasExcedentes + " horas.";
        } else {
            mensagem = username + " chegou no seu horário.";
        }

        Email email = new Email("kadukitesesi@gmail.com", "Ponto de Entrada", mensagem);
        emailService.enviarEmail(email);

        return mensagem;
    }

    public String baterPontoSaida(String nome) {
        LocalTime horarioSaida = LocalTime.of(18, 0);
        saida = LocalTime.now();
        UserModel user = userRepository.findByNome(nome);
        String mensagem;

        if (saida.isAfter(horarioSaida)) {
            long horasExcedentes = Duration.between(horarioSaida, saida).toHours();
            mensagem = user.getUsername() + " tem " + horasExcedentes + " horas excedentes.";
        } else if (saida.equals(horarioSaida)) {
            mensagem = user.getUsername() + " está saindo em seu horário.";
        } else {
            mensagem = user.getUsername() + " está saindo às: " + Instant.now();
        }

        Email email = new Email("kadukitesesi@gmail.com", "Ponto de Saída", mensagem);
        emailService.enviarEmail(email);

        return mensagem;
    }

    public long calculaHorasTrabalhadas(String username) {
        if (entrada == null || saida == null) {
            throw new IllegalStateException("Entrada ou saída não registrada.");
        }
        long horasTrabalhadasDia = Duration.between(entrada, saida).toHours();
        Optional<UserModel> usuario = userRepository.findByUsername(username);
        if (usuario.isPresent()) {
            UserModel userModel = usuario.get();
            long horasTrabalhadasUsuario = userModel.getHorasTrabalhadas();
            userModel.setHorasTrabalhadas(horasTrabalhadasUsuario + horasTrabalhadasDia);
            userRepository.save(userModel);
        }
        return horasTrabalhadasDia;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetarHorasDoMes() {
        List<UserModel> usuarios = userRepository.findAll();
        for (UserModel usuario : usuarios) {
            usuario.setHorasTrabalhadas(0);
        }
        userRepository.saveAll(usuarios);
    }
}
