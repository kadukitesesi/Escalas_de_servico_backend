package com.kadukitesesi.escalatrabalho.api.auth;


import com.kadukitesesi.escalatrabalho.service.EscalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller("/adm")
public class EmpresaController {

    @Autowired
    private EscalaService escalaService;

    @PostMapping("/escalar")
    public ResponseEntity<Void> escalarFuncionario(@RequestParam String nome, @RequestParam Date dataServico) {
      escalaService.criarEscala(nome, dataServico);
      return ResponseEntity.ok().build();
    }

}
