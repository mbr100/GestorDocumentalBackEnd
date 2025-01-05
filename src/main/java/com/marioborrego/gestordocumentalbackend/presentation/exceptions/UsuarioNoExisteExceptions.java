package com.marioborrego.gestordocumentalbackend.presentation.exceptions;

public class UsuarioNoExisteExceptions extends RuntimeException {
    public UsuarioNoExisteExceptions(String message) {
        super(message);
    }
}
