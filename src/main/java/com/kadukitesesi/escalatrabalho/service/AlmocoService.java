package com.kadukitesesi.escalatrabalho.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AlmocoService {

    private LocalTime inicioAlmoco;
    private LocalTime fimAlmoco;

    public String iniciarAlmoco() {
        inicioAlmoco = LocalTime.now();
        return "Almoço iniciado às " + inicioAlmoco;
    }

    public String terminarAlmoco() {
        if (inicioAlmoco == null) {
            return "Você ainda não almoçou";
        }
        fimAlmoco = LocalTime.now();
        long tempoAlmoco = Duration.between(inicioAlmoco, fimAlmoco).toMinutes();

        inicioAlmoco = null;

        return "Almoço terminado às " + fimAlmoco + ", almoço durou " + tempoAlmoco;
    }



}
