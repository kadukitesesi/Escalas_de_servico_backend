package com.kadukitesesi.escalatrabalho.api.model.user.dtos;

import com.kadukitesesi.escalatrabalho.api.model.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RegisterDto(@NotNull String userName, @NotNull String cargo, @NotNull BigDecimal salario,  @NotNull String email,@NotNull String password, @NotNull UserRole role ) {
    
}