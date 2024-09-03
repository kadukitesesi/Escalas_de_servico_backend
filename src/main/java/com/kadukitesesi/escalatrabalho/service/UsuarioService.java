package com.kadukitesesi.escalatrabalho.service;


import com.kadukitesesi.escalatrabalho.api.model.RegistroDTO;
import com.kadukitesesi.escalatrabalho.infraestrutura.exceptions.UsuarioJaExisteException;
import com.kadukitesesi.escalatrabalho.infraestrutura.model.Usuario;
import com.kadukitesesi.escalatrabalho.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private UserRepository userRepository;
    private RegistrationService registrationService;
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UserRepository userRepository, RegistrationService registrationService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }



    public ResponseEntity<RegistroDTO> criarUsuario(RegistroDTO usuario) throws UsuarioJaExisteException {
        registrationService.registerUser(usuario);
        return ResponseEntity.ok(usuario);
    }

    public ResponseEntity<Optional<Usuario>> buscarUsuarioId(Long id) {
        Optional<Usuario> buscado = Optional.of(userRepository.getById(id));
        if (buscado.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        return ResponseEntity.ok(buscado);
    }

    public ResponseEntity<List<Usuario>> buscarUsuarios() {
        List<Usuario> usuarios = userRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    public void excluirUsuario(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new InvalidParameterException("Usuário não encontrado");
        }
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            if (userRepository.findByUsername("admin_username").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin_username");
                admin.setNome("Administrador");
                admin.setRole("ROLE_ADMINISTRADOR");
                admin.setCargo("Chefe");
                admin.setNascimento(new Date()); // Ajuste para a data de nascimento real
                admin.setPassword(passwordEncoder.encode("admin_password"));
                admin.setDataServico(List.of()); // Lista de datas de serviço vazia

                userRepository.save(admin);
            }
        };


    }
}
