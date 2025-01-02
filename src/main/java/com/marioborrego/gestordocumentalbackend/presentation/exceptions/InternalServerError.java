package com.marioborrego.gestordocumentalbackend.presentation.exceptions;

public class InternalServerError extends RuntimeException{
    public InternalServerError(String message) {
        super(message);
    }
}
