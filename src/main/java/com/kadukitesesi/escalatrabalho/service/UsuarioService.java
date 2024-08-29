package com.kadukitesesi.escalatrabalho.service;


import com.kadukitesesi.escalatrabalho.infraestrutura.exceptions.UsuarioJaExisteException;
import com.kadukitesesi.escalatrabalho.infraestrutura.model.Usuario;
import com.kadukitesesi.escalatrabalho.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private UserRepository userRepository;

    public UsuarioService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Usuario> criarUsuario(Usuario usuario) throws UsuarioJaExisteException {
        if (usuario.getNome() != null) {
            throw new UsuarioJaExisteException("Este usuário já existe");
        }
       Usuario novoUsuario = userRepository.save(usuario);
        return ResponseEntity.ok(novoUsuario);
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


}
