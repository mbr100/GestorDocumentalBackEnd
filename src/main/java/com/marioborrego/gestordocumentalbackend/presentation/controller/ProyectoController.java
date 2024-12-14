package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.CarpetaService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.CrearProyectoDTO;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.ProyectoService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.ListarProyectoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.ListarProyectoEmpleadoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("api/proyectos")
@Tag(name = "Proyectos", description = "Gestión de proyectos")
public class ProyectoController {
    private final ProyectoService proyectoService;
    private final CarpetaService carpetaService;
    private final Logger logger = LoggerFactory.getLogger(ProyectoController.class);

    public ProyectoController(ProyectoService proyectoService, CarpetaService carpetaService) {
        this.proyectoService = proyectoService;
        this.carpetaService = carpetaService;
    }

    @Operation(summary = "Listar todos los proyectos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyectos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron proyectos")
    })
    @GetMapping()
    @RequestMapping(method = RequestMethod.GET, value = "/listarTodosProyectos")
    public ResponseEntity<List<ListarProyectoDTO>> listarProyectos() {
        List<ListarProyectoDTO> proyectos = proyectoService.getAllProyectos();
        if (proyectos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(proyectos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(proyectos);
    }

    @Operation(summary = "Listar todos los proyectos de un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyectos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron proyectos")
    })
    @GetMapping()
    @RequestMapping(method = RequestMethod.GET, value = "/listarproyectos/{idEmpleado}")
    public ResponseEntity<List<ListarProyectoEmpleadoDTO>> listarProyectosEmpleado(@PathVariable int idEmpleado) {

        List<ListarProyectoEmpleadoDTO> proyectos = proyectoService.getProyectosEmpleado(idEmpleado);
        if (proyectos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(proyectos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(proyectos);
    }

    @Operation(summary = "Crear un proyecto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyecto creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del proyecto incorrectos")
    })
    @PostMapping()
    public ResponseEntity<Map<String, String>> crearProyecto(@RequestBody CrearProyectoDTO proyecto) {
        Map<String, String> response = new HashMap<>();
        HttpStatus status;
        logger.info("Creando proyecto: " + proyecto.getNombreEmpleado());
        if (proyecto.getTitulo() == null || proyecto.getTitulo().isEmpty()) {
            response.put("status", "error");
            response.put("message", "El titulo del proyecto no puede estar vacio");
            status = HttpStatus.BAD_REQUEST;
        } else if (proyecto.getCliente() == null || proyecto.getCliente().isEmpty()) {
            response.put("status", "error");
            response.put("message", "El cliente del proyecto no puede estar vacio");
            status = HttpStatus.BAD_REQUEST;
        } else if (proyecto.getAno() > LocalDate.now().getYear()) {
            response.put("status", "error");
            response.put("message", "El año del proyecto no puede ser superiro al actual");
            status = HttpStatus.BAD_REQUEST;
        } else {
            response = proyectoService.crearProyecto(proyecto);
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(response);
    }

    @Operation(summary = "Actualizar un proyecto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyecto actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del proyecto incorrectos")
    })
    @PutMapping()
    @RequestMapping(method = RequestMethod.PUT, value = "/actualizarProyecto")
    public ResponseEntity<Map<String, String>> actualizarProyecto(@RequestBody ListarProyectoDTO proyecto) {

        Map<String, String> response = new HashMap<>();
        HttpStatus status;
        if (proyecto.getTitulo() == null || proyecto.getTitulo().isEmpty()) {
            response.put("status", "error");
            response.put("message", "El titulo del proyecto no puede estar vacio");
            status = HttpStatus.BAD_REQUEST;
        } else if (proyecto.getCliente() == null || proyecto.getCliente().isEmpty()) {
            response.put("status", "error");
            response.put("message", "El cliente del proyecto no puede estar vacio");
            status = HttpStatus.BAD_REQUEST;
        } else if (proyecto.getAno() > LocalDate.now().getYear()) {
            response.put("status", "error");
            response.put("message", "El año del proyecto no puede estar vacio");
            status = HttpStatus.BAD_REQUEST;
        } else {
            response = proyectoService.actualizarProyecto(proyecto);
            status = HttpStatus.OK;
        }
        return ResponseEntity.status(status).body(response);
    }

    @Operation(summary = "Listar todos los documentos de un proyecto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron documentos")
    })
    @GetMapping()
    @RequestMapping(method = RequestMethod.GET, value = "proyecto/{id}/documentos")
    public ResponseEntity<Map<String,Object>> listarDocumentosProyecto(@PathVariable String id) throws IOException {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;
        if (id == null || id.isEmpty()) {
            response.put("status", "error");
            response.put("message", "El id del proyecto no puede estar vacio");
            status = HttpStatus.BAD_REQUEST;
        } else {
            status = HttpStatus.OK;
            response.put("carpeta",carpetaService.archivosProyectoParaEmpleados(id));
        }
        return ResponseEntity.status(status).body(response);
    }
}
