package com.kadukitesesi.escalatrabalho.service;

import com.kadukitesesi.escalatrabalho.infraestrutura.model.Usuario;
import com.kadukitesesi.escalatrabalho.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EscalaService {

    @Autowired
    private UserRepository userRepository;

    public void criarEscala(String nome, Date dataServico) {
        Optional<Usuario> usuarioBuscado = userRepository.findByUsername(nome);
        if (usuarioBuscado.isPresent()) {
            Usuario usuario = usuarioBuscado.get();

            List<Date> servicos = usuario.getDataServico();
            if (servicos.isEmpty()) {
                servicos = new ArrayList<>();
            }

            servicos.add(dataServico);
            usuario.setDataServico(servicos);

            userRepository.save(usuario);

        }
    }
}
