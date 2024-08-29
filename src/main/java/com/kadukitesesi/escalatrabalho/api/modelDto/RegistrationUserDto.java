package com.kadukitesesi.escalatrabalho.api.modelDto;

import java.util.Date;

public record RegistrationUserDto(String username,
         String nome,
         String role,
         String cargo,
         Date nascimento,
         String password) {
}
