package com.kadukitesesi.escalatrabalho.api.controller;

import com.kadukitesesi.escalatrabalho.api.model.LoginDTO;
import com.kadukitesesi.escalatrabalho.api.model.RegistroDTO;
import com.kadukitesesi.escalatrabalho.security.AuthenticationService;
import com.kadukitesesi.escalatrabalho.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginDTO login) {
        return authenticationService.authenticate(login.username(), login.password());
    }

    @PostMapping("/admin/registro")
    public ResponseEntity<String> createUser(@RequestBody RegistroDTO usuario) {
        try {
            usuarioService.criarUsuario(usuario);
            return new ResponseEntity<>("Usuário registrado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Não foi possível criar o usuário", HttpStatus.BAD_REQUEST);
        }
    }
}
