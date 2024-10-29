package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.CarpetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/documentos")
@Tag(name = "Documentos", description = "Gestión de documentos")
public class DocumentosController {
    private final CarpetaService carpetaService;
    private final Logger logger = LoggerFactory.getLogger(DocumentosController.class);

    public DocumentosController(CarpetaService carpetaService) {
        this.carpetaService = carpetaService;
    }

    @Operation(summary = "Subir documento a proyecto", description = "Subir documento a proyecto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento subido correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al subir el documento")
    })
    @PutMapping("/{idProyecto}")
    public ResponseEntity<?> subirDocumentoProyecto(@PathVariable String idProyecto, @RequestParam("carpeta") String carpeta, @RequestParam("documento") MultipartFile documento) throws IOException {
        Map<String, String> response = new HashMap<>();
        HttpStatus status ;
        if (idProyecto.isEmpty()){
            response.put("message", "El id del proyecto no puede estar vacío");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        } else if (carpeta.isEmpty()){
            response.put("message", "La carpeta no puede estar vacía");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        } else if (documento.isEmpty()){
            response.put("message", "El documento no puede estar vacío");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        boolean result = carpetaService.guardarDocumentoProyecto(idProyecto, carpeta, documento);
        if (result){
            response.put("message", "Documento subido correctamente");
            response.put("status", "success");
            status = HttpStatus.OK;
        } else {
            response.put("message", "Error al subir el documento");
            response.put("status", "error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

    @Operation(summary = "Subir documento a carpeta", description = "Subir documento a carpeta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento subido correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al subir el documento")
    })
    @PutMapping("/{idProyecto}/aceptar")
    public ResponseEntity<?> aceptarDocumento(@PathVariable String idProyecto, @RequestParam String ruta, @RequestParam String documento) throws IOException {
        logger.info("Aceptando documento: {} en la ruta: {} del proyecto: {}", documento, ruta, idProyecto);
        Map<String, String> response = new HashMap<>();
        HttpStatus status;
        if (idProyecto.isEmpty()){
            response.put("message", "El id del proyecto no puede estar vacío");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        if (ruta.isEmpty()){
            response.put("message", "La ruta no puede estar vacía");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        if (documento.isEmpty()){
            response.put("message", "El documento no puede estar vacío");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        boolean result = carpetaService.aceptarDocumento(idProyecto, ruta, documento);
        if (result){
            response.put("message", "Documento aceptado correctamente");
            response.put("status", "success");
            status = HttpStatus.OK;
        } else {
            response.put("message", "Error al aceptar el documento");
            response.put("status", "error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }

    @Operation(summary = "Rechazar documento", description = "Rechazar documento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento rechazado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al rechazar el documento")
    })
    @PutMapping("/{idProyecto}/rechazar")
    public ResponseEntity<?> rechazarDocumentos(@PathVariable String idProyecto, @RequestParam String ruta, @RequestParam String documento) throws IOException {
        logger.info("Rechazando documento: {} en la ruta: {} del proyecto: {}", documento, ruta, idProyecto);
        Map<String, String> response = new HashMap<>();
        HttpStatus status;
        if (idProyecto.isEmpty()){
            response.put("message", "El id del proyecto no puede estar vacío");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        if (ruta.isEmpty()){
            response.put("message", "La ruta no puede estar vacía");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        if (documento.isEmpty()){
            response.put("message", "El documento no puede estar vacío");
            response.put("status", "error");
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        boolean result = carpetaService.rechazarDocumento(idProyecto, ruta, documento);
        if (result){
            response.put("message", "Documento aceptado correctamente");
            response.put("status", "success");
            status = HttpStatus.OK;
        } else {
            response.put("message", "Error al aceptar el documento");
            response.put("status", "error");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(response);
    }
}
