package com.kadukitesesi.escalatrabalho.api.model.user.dtos;

import com.kadukitesesi.escalatrabalho.api.model.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String email,@NotNull String password, @NotNull UserRole role ) {
    
}
