package com.kadukitesesi.escalatrabalho.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsService;

    public AuthenticationService(JwtService jwtService, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public String authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            return jwtService.generateToken(authentication);
        } else {
            throw new BadCredentialsException("Senha inválida");
        }
    }
}
