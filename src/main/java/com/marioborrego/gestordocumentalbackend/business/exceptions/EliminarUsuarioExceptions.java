package com.marioborrego.gestordocumentalbackend.business.exceptions;

import org.springframework.http.HttpStatus;

public class EliminarUsuarioExceptions extends RuntimeException{
    public EliminarUsuarioExceptions(String message) {
        super(message);
    }
}
