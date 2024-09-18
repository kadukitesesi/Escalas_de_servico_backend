package com.kadukitesesi.escalatrabalho.service;

import com.kadukitesesi.escalatrabalho.api.model.user.models.UserModel;
import com.kadukitesesi.escalatrabalho.api.model.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    public String baterPontoEntrada() {
        LocalTime horarioEntrada = LocalTime.of(8, 0);
        entrada = LocalTime.now();

        if (entrada.isAfter(horarioEntrada)) {
            long horasExcedentes = Duration.between(horarioEntrada, entrada).toHours();
            System.out.println("Horas atrasado: " + horasExcedentes);
        }

        return entrada.equals(horarioEntrada) || entrada.isBefore(horarioEntrada)
                ? "Você chegou no seu horário"
                : "Você está chegando atrasado";
    }

    public String baterPontoSaida() {
        LocalTime horarioSaida = LocalTime.of(18, 0);
        saida = LocalTime.now();

        if (saida.isAfter(horarioSaida)) {
            long horasExcedentes = Duration.between(horarioSaida, saida).toHours();
            System.out.println("Horas excedentes: " + horasExcedentes);
        }

        return saida.equals(horarioSaida)
                ? "Você saiu no seu horário"
                : "Você está saindo mais cedo";
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
