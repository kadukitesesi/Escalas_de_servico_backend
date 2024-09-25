package com.kadukitesesi.escalatrabalho.api.controller;


import com.kadukitesesi.escalatrabalho.service.EscalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/adm")
public class EmpresaController {

    @Autowired
    private EscalaService escalaService;

    @PostMapping("/escalar/{nome}/{dataServico}")
    public ResponseEntity<Void> escalarFuncionario(@PathVariable String nome, @PathVariable Date dataServico) {
      escalaService.criarEscala(nome, dataServico);
      return ResponseEntity.ok().build();
    }

}
