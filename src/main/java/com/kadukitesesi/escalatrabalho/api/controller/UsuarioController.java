package com.kadukitesesi.escalatrabalho.api.controller;


import com.kadukitesesi.escalatrabalho.api.modelDto.RegistrationUserDto;
import com.kadukitesesi.escalatrabalho.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UsuarioController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("login")
    public String authenticate(
            Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }

    @PostMapping("registro")
    public ResponseEntity<String> createUser(@RequestBody RegistrationUserDto userDto) {
        try {
            registrationService.registerUser(userDto);
            return new ResponseEntity<>("Usuário registrado com sucesso!", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Não foi possível criar o usuário", HttpStatus.BAD_REQUEST);
        }
    }
}
