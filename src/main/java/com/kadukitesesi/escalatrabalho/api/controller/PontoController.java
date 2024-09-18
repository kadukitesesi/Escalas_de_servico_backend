package com.kadukitesesi.escalatrabalho.api.controller;

import com.kadukitesesi.escalatrabalho.service.AlmocoService;
import com.kadukitesesi.escalatrabalho.service.EscalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ponto")
public class PontoController {

    @Autowired
    private AlmocoService almocoService;

    @Autowired
    private EscalaService escalaService;


    @GetMapping("/saida")
    public ResponseEntity<String> baterPontoSaida() {
        String resposta = escalaService.baterPontoSaida();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/entrada")
    public ResponseEntity<String> baterPontoEntrada() {
        String resposta = escalaService.baterPontoEntrada();
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
}
