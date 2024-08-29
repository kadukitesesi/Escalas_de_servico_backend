package com.kadukitesesi.escalatrabalho.repository;

import com.kadukitesesi.escalatrabalho.infraestrutura.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
    public interface UserRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByUsername(String nome);
}
