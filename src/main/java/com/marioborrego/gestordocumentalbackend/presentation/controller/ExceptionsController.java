package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.exceptions.ActualizarUsuarioExceptions;
import com.marioborrego.gestordocumentalbackend.business.exceptions.CrearUsuarioExceptions;
import com.marioborrego.gestordocumentalbackend.business.exceptions.EliminarUsuarioExceptions;
import com.marioborrego.gestordocumentalbackend.business.exceptions.InternalServerError;
import com.marioborrego.gestordocumentalbackend.presentation.dto.responseDTO.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(EliminarUsuarioExceptions.class)
    public ResponseEntity<ResponseDTO> eliminarUsuarioExceptions(EliminarUsuarioExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ResponseDTO> internalServerError(InternalServerError e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(CrearUsuarioExceptions.class)
    public ResponseEntity<ResponseDTO> crearUsuarioExceptions(CrearUsuarioExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(ActualizarUsuarioExceptions.class)
    public ResponseEntity<ResponseDTO> actualizarUsuarioException(ActualizarUsuarioExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }
}
