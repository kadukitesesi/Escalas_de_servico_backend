package com.kadukitesesi.escalatrabalho.service;

import com.kadukitesesi.escalatrabalho.api.model.user.models.UserModel;
import com.kadukitesesi.escalatrabalho.api.model.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EscalaService {

    @Autowired
    private UserRepository userRepository;

    public void criarEscala(String nome, Date dataServico) {
        Optional<UserModel> usuarioBuscado = userRepository.findByUsername(nome);
        if (usuarioBuscado.isPresent()) {
            UserModel usuario = usuarioBuscado.get();

            List<Date> servicos = usuario.getDataServico();
            if (servicos.isEmpty()) {
                servicos = new ArrayList<>();
            }
                servicos.add(dataServico);
                usuario.setDataServico(servicos);

            userRepository.save(usuario);

        }
    }

    public String baterPontoEntrada() {
        LocalTime horarioEntrada = LocalTime.of(8,0);
        LocalTime entrada = LocalTime.now();
        if (entrada.isAfter(horarioEntrada)) {
            Integer horasExcedentes = entrada.compareTo(horarioEntrada);
            System.out.println("Horas atrasado" + horasExcedentes);
        }

        if (entrada.equals(horarioEntrada) || entrada.isBefore(horarioEntrada)) {
            return "Você chegou no  seu horário";
        }
        return "você está saindo mais cedo";

    }

    public String baterPontoSaida() {
        LocalTime horarioSaida = LocalTime.of(18,0);
        LocalTime saida = LocalTime.now();
        if (saida.isAfter(horarioSaida)) {
            Integer horasExcedentes = saida.compareTo(horarioSaida);
            System.out.println("Horas excedentes" + horasExcedentes);
        }

        if (saida.equals(horarioSaida)) {
            return "Você saiu no  seu horário";
        }
        return "você está saindo mais cedo";
    }

    }


