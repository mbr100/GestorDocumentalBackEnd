package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.presentation.dto.EmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.services.EmpleadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/empleados")
@Tag(name = "Empleados", description = "Gestión de empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;
    private final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @Operation(summary = "Obtener todos los empleados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron empleados"),
            @ApiResponse(responseCode = "500", description = "Fallo al recuperar los empleados")
    })
    @GetMapping()
    public ResponseEntity<List<EmpleadoDTO>> obtenerTodosLosEmpleados() {
        try {
            List<EmpleadoDTO> empleados = empleadoService.obtenerTodosLosEmpleados();
            if (empleados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(empleados);
            }
            return ResponseEntity.status(HttpStatus.OK).body(empleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
