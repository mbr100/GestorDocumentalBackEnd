package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.presentation.dto.responseDTO.ResponseDTO;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.*;
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

    @ExceptionHandler(SubirDocumentoExceptions.class)
    public ResponseEntity<ResponseDTO> subirDocumentoExceptions(SubirDocumentoExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(ActualizarProyectoExceptions.class)
    public ResponseEntity<ResponseDTO> actualizarProyectoExceptions(ActualizarProyectoExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(CrearProyectoExceptions.class)
    public ResponseEntity<ResponseDTO> crearProyectoExceptions(CrearProyectoExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(DocumentosProyectoExceptions.class)
    public ResponseEntity<ResponseDTO> documentosProyectoExceptions(DocumentosProyectoExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(CrearPuntoNoConformidadExceptions.class)
    public ResponseEntity<ResponseDTO> crearPuntoNoConformidadExceptions(CrearPuntoNoConformidadExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

    @ExceptionHandler(NcNoEncontradaExceptions.class)
    public ResponseEntity<ResponseDTO> ncNoEncontradaExceptions(NcNoEncontradaExceptions e) {
        String status = "error";
        String message = e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(status, message));
    }

}
