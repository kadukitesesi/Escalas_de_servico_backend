package com.kadukitesesi.escalatrabalho.service;

import com.kadukitesesi.escalatrabalho.api.model.user.models.UserModel;
import com.kadukitesesi.escalatrabalho.api.model.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<UserModel> usuarioBuscado = userRepository.findByUsername(nome);
        if (usuarioBuscado.isPresent()) {
            UserModel usuario = usuarioBuscado.get();

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
