package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.presentation.exceptions.ActualizarProyectoExceptions;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.CrearProyectoExceptions;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.DocumentosProyectoExceptions;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.CarpetaService;
import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
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
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Operation(summary = "Listar todos los proyectos de un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyectos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron proyectos")
    })
    @GetMapping("/listarproyectos")
    public ResponseEntity<List<ListarProyectoEmpleadoDTO>> listarProyectosEmpleado() {
        Usuario u = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ListarProyectoEmpleadoDTO> proyectos;
        if (u.getRol().getTipoRol() == TipoRol.ADMINISTRADOR){
            proyectos = proyectoService.getAllProyectosAdministrador();
        } else{
            proyectos = proyectoService.getProyectosEmpleado(u.getIdUsuario());
        }
        if (proyectos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(proyectos);
        }
        return ResponseEntity.status(HttpStatus.OK).body(proyectos);
    }

    @Operation(summary = "Listar todos los proyectos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proyectos obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron proyectos")
    })
    @GetMapping("/listarTodosProyectos")
    public ResponseEntity<List<ListarProyectoDTO>> listarTodosProyectos() {
        List<ListarProyectoDTO> proyectos = proyectoService.getAllProyectos();
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
        Map<String, String> response;
        HttpStatus status;
        logger.info("Creando proyecto: {}", proyecto.getNombreEmpleado());
        if (proyecto.getTitulo() == null || proyecto.getTitulo().isEmpty()) {
            throw new CrearProyectoExceptions("El titulo del proyecto no puede estar vacio");
        } else if (proyecto.getCliente() == null || proyecto.getCliente().isEmpty()) {
            throw new CrearProyectoExceptions("El cliente del proyecto no puede estar vacio");
        } else if (proyecto.getAno() > LocalDate.now().getYear()) {
            throw new CrearProyectoExceptions("El año del proyecto no puede ser superior al actual");
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
    @PutMapping("/actualizarProyecto")
    public ResponseEntity<Map<String, String>> actualizarProyecto(@RequestBody ListarProyectoDTO proyecto) {

        Map<String, String> response;
        HttpStatus status;
        if (proyecto.getTitulo() == null || proyecto.getTitulo().isEmpty()) {
            throw new ActualizarProyectoExceptions("El titulo del proyecto no puede estar vacio");
        } else if (proyecto.getCliente() == null || proyecto.getCliente().isEmpty()) {
            throw new ActualizarProyectoExceptions("El cliente del proyecto no puede estar vacio");
        } else if (proyecto.getAno() > LocalDate.now().getYear()) {
            throw new ActualizarProyectoExceptions("El año del proyecto no puede ser superior al actual");
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
    @GetMapping("proyecto/{id}/documentos")
    public ResponseEntity<Map<String,Object>> listarDocumentosProyecto(@PathVariable String id) throws IOException {
        Map<String, Object> response = new HashMap<>();
        HttpStatus status;
        if (id == null || id.isEmpty()) {
            throw new DocumentosProyectoExceptions("El id del proyecto no puede estar vacio");
        } else {
            status = HttpStatus.OK;
            response.put("carpeta",carpetaService.archivosProyectoParaEmpleados(id));
        }
        return ResponseEntity.status(status).body(response);
    }
}
