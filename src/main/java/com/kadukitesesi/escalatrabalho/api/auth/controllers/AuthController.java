package com.kadukitesesi.escalatrabalho.api.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadukitesesi.escalatrabalho.api.auth.services.AuthorizationService;
import com.kadukitesesi.escalatrabalho.api.model.user.dtos.AuthetinticationDto;
import com.kadukitesesi.escalatrabalho.api.model.user.dtos.RegisterDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
   
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDto authetinticationDto){
        return authorizationService.login(authetinticationDto);
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register (@RequestBody RegisterDto registerDto){
        return authorizationService.register(registerDto);
    }
}
