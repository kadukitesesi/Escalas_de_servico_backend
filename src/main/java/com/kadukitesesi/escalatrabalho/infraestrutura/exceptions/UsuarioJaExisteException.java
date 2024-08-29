package com.kadukitesesi.escalatrabalho.infraestrutura.exceptions;

public class UsuarioJaExisteException extends Exception {

    public UsuarioJaExisteException(String message) {
        super(message);
    }
}
