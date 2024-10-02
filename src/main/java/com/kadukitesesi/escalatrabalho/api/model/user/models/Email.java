package com.kadukitesesi.escalatrabalho.api.model.user.models;

import lombok.Builder;
import lombok.Singular;

import java.util.Map;

@Builder
public record Email(String para, String assunto, String mensagem, @Singular("variavel") Map<String, Object> variaveis) {
}
