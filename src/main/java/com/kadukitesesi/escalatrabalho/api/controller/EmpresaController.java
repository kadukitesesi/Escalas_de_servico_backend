package com.kadukitesesi.escalatrabalho.api.controller;


import com.kadukitesesi.escalatrabalho.api.model.user.dtos.Funcionarios;
import com.kadukitesesi.escalatrabalho.service.EscalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/adm")
public class EmpresaController {

    @Autowired
    private EscalaService escalaService;

    @PostMapping("/escalar/{nome}/{dataServico}")
    public ResponseEntity<Void> escalarFuncionario(@PathVariable String nome, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") Date dataServico) {
      escalaService.criarEscala(nome, dataServico);
      return ResponseEntity.ok().build();
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Funcionarios>> funcionarios() {
        List<Funcionarios> funcionarios = escalaService.consultarFuncionarios();
        return ResponseEntity.status(200).body(funcionarios);
    }

}
