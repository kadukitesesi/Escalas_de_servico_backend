package com.kadukitesesi.escalatrabalho.service;

import com.kadukitesesi.escalatrabalho.api.model.RegistroDTO;
import com.kadukitesesi.escalatrabalho.infraestrutura.exceptions.UsuarioJaExisteException;
import com.kadukitesesi.escalatrabalho.infraestrutura.model.Usuario;
import com.kadukitesesi.escalatrabalho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;



    public void registerUser(RegistroDTO usuario) throws UsuarioJaExisteException {
        var existingUser = userRepository.findByUsername(usuario.username());

        if (existingUser.isPresent()) {
            throw new UsuarioJaExisteException("Usuário já existe!");
        }

        Usuario user = new Usuario();
        user.setUsername(usuario.username());
        user.setPassword(passwordEncoder.encode(usuario.password()));
        user.setNome(usuario.nome());
        user.setCargo(usuario.cargo());
        user.setRole("ROLE_USUARIO");
        user.setNascimento(usuario.nascimento());
        user.setDataServico(List.of());
        userRepository.save(user);
    }
}
