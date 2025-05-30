package com.kadukitesesi.escalatrabalho.service;

import com.kadukitesesi.escalatrabalho.api.model.user.dtos.Funcionarios;
import com.kadukitesesi.escalatrabalho.api.model.user.models.Email;
import com.kadukitesesi.escalatrabalho.api.model.user.models.UserModel;
import com.kadukitesesi.escalatrabalho.api.model.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EscalaService {

    private LocalTime entrada;
    private LocalTime saida;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public void criarEscala(String nome, Date dataServico) {
        userRepository.findByUsername(nome).ifPresent(usuario -> {
            List<Date> servicos = usuario.getDataServico();
            if (servicos == null) {
                servicos = new ArrayList<>();
            }
            servicos.add(dataServico);
            usuario.setDataServico(servicos);
            userRepository.save(usuario);
        });
    }


    public String baterPontoEntrada(String username) {
        LocalTime horarioEntrada = LocalTime.of(8, 0);
        entrada = LocalTime.now();
        saida = LocalTime.of(18,0);
        String horaFormatada = entrada.format(formatter);
        String mensagem = "";

        UserModel emailUser = userRepository.findByUsername(username).get();

        if (entrada.isAfter(horarioEntrada) && entrada.isBefore(saida)) {
            long horasExcedentes = Duration.between(horarioEntrada, entrada).toHours();
            mensagem = username + " está atrasado em " + horasExcedentes + " horas.";
        }
            mensagem = username + " chegou: " + horaFormatada;

        var email = Email.builder()
                        .para(emailUser.getEmail())
                        .assunto("Ponto de Entrada")
                        .mensagem("Template_email.html")
                        .variavel("ponto", mensagem )
                        .variavel("nome", emailUser.getUsername())
                        .build();
        emailService.enviarEmail(email);

        return mensagem;
    }

    public String baterPontoSaida(String nome) {
        LocalTime horarioSaida = LocalTime.of(18, 0);
        saida = LocalTime.now();
        String horaFormatada = saida.format(formatter);
        UserModel user = userRepository.findByUsername(nome).get();
        String mensagem;

        if (saida.isAfter(horarioSaida)) {
            long horasExcedentes = Duration.between(horarioSaida, saida).toHours();
            mensagem = user.getUsername() + " tem " + horasExcedentes + " horas excedentes.";
        } else if (saida.equals(horarioSaida)) {
            mensagem = user.getUsername() + " está saindo em seu horário.";
        } else {
            mensagem = String.format(user.getUsername() + " está saindo às: " + horaFormatada);
        }

        var email = Email.builder()
                .para(user.getEmail())
                .assunto("Ponto de saída")
                .mensagem("Template_email.html")
                .variavel("ponto", mensagem )
                .variavel("nome", user.getUsername())
                .build();
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

            String mensagem = userModel.getUsername() + " até o momento tem " + horasTrabalhadasUsuario + " horas de trabalho.";

            var email = Email.builder()
                    .para(userModel.getEmail())
                    .assunto("Horas trabalhadas")
                    .mensagem("Template_email.html")
                    .variavel("ponto", mensagem )
                    .variavel("nome", userModel.getUsername())
                    .build();
            emailService.enviarEmail(email);
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

    public List<Funcionarios> consultarFuncionarios() {
        List<UserModel> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(
                        usuario -> new Funcionarios(
                                usuario.getUsername(),
                                usuario.getSalario(),
                                usuario.getCargo(),
                                usuario.getCreatedAt()
                        )
                ).collect(Collectors.toList());
    }
}
