package com.kadukitesesi.escalatrabalho.api.controller;

import com.kadukitesesi.escalatrabalho.service.AlmocoService;
import com.kadukitesesi.escalatrabalho.service.EmailService;
import com.kadukitesesi.escalatrabalho.service.EscalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ponto")
public class PontoController {

    @Autowired
    private AlmocoService almocoService;

    @Autowired
    private EscalaService escalaService;


    @GetMapping("/saida/{nome}")
    public ResponseEntity<String> baterPontoSaida(@PathVariable String nome) {
        String resposta = escalaService.baterPontoSaida(nome);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/entrada/{nome}")
    public ResponseEntity<String> baterPontoEntrada(@PathVariable String nome) {
        String resposta = escalaService.baterPontoEntrada(nome);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/iniciar-almoço")
    public ResponseEntity<String> iniciarAlmoco() {
        String resposta = almocoService.iniciarAlmoco();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/terminar-almoço")
    public ResponseEntity<String> terminarAlmoco() {
        String resposta = almocoService.terminarAlmoco();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/horas-trabalhadas/{usuario}")
    public ResponseEntity<String> verHorasTrabalhadas(@PathVariable String usuario) {
        long horasTrabalhadas = escalaService.calculaHorasTrabalhadas(usuario);
        String resposta = "Horas trabalhadas: " + horasTrabalhadas;
        return ResponseEntity.ok(resposta);
    }
}
