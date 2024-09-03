package com.kadukitesesi.escalatrabalho.api.model;

import java.util.Date;

public record RegistroDTO(String username, String password, String cargo, Date nascimento, String nome) {
}
