package com.kadukitesesi.escalatrabalho.api.model.user.dtos;

import java.math.BigDecimal;
import java.util.Date;

public record Funcionarios (String nome, BigDecimal salario, String cargo, Date contratacao){
}
