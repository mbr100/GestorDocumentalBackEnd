package com.marioborrego.gestordocumentalbackend.business.exceptions;

public class InternalServerError extends RuntimeException{
    public InternalServerError(String message) {
        super(message);
    }
}
