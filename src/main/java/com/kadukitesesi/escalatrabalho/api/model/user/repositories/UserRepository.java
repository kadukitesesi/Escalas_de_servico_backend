package com.kadukitesesi.escalatrabalho.api.model.user.repositories;

import java.util.Optional;
import java.util.UUID;

import com.kadukitesesi.escalatrabalho.api.model.user.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID>{
    UserDetails findByEmail(String email);
    Optional<UserModel> findByUsername(String username);
    UserModel findByNome(String nome);
}
