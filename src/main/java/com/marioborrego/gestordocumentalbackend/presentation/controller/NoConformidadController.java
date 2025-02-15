package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.ProyectoService;
import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.*;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ncs")
public class NoConformidadController {
    private final NoConformidadService noConformidadService;
    private final ProyectoService proyectoService;
    private final Logger log = LoggerFactory.getLogger(NoConformidadController.class);

    public NoConformidadController(NoConformidadService noConformidadService, ProyectoService proyectoService) {
        this.noConformidadService = noConformidadService;
        this.proyectoService = proyectoService;
    }

    private Usuario getAuthenticatedUser() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuario == null) {
            log.error("Usuario no encontrado");
            throw new UsuarioNoExisteExceptions("Usuario no encontrado");
        }
        return usuario;
    }

    @Operation(summary = "Ver no conformidades de un proyecto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "No conformidades obtenidas correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron no conformidades"),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<NoConformidadesProyectoDto>> verNcsProyecto(@PathVariable String idProyecto) {
        getAuthenticatedUser();
        if (!proyectoService.existeProyecto(idProyecto)) {
            return ResponseEntity.notFound().build();
        }
        List<NoConformidadesProyectoDto> ncs = noConformidadService.noConformidadesPorProyecto(idProyecto).stream()
                .map(NoConformidadesProyectoDto::new).toList();
        return ncs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ncs);
    }

    @Operation(summary = "Responder no conformidad")
    @PostMapping("/responder")
    public ResponseEntity<?> responderNoConformidad(@RequestBody @Valid RespuestaPuntoNoConformidad respuesta) {
        getAuthenticatedUser();
        return respuesta.getContenido() == null ? ResponseEntity.badRequest().build() :
                ResponseEntity.status(noConformidadService.responderNoConformidad(respuesta) ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Cerrar punto no conformidad")
    @PutMapping("/cerrarPuntoNc/{idPuntoNc}")
    public ResponseEntity<?> cerrarPuntoNc(@PathVariable int idPuntoNc) {
        Usuario usuario = getAuthenticatedUser();
        if (usuario.getRol().getTipoRol() != TipoRol.EMPLEADO) {
            throw new NoConformidadExceptions("No tiene permisos para cerrar la no conformidad");
        }
        return noConformidadService.cerrarPuntoNc((long) idPuntoNc) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Crear punto no conformidad")
    @PostMapping("/crearPuntoNoConformidad")
    public ResponseEntity<?> crearPuntoNoConformidad(@RequestBody NuevoPuntoNcDTO nuevoPuntoNcDTO) {
        Usuario usuario = getAuthenticatedUser();
        if (usuario.getRol().getTipoRol() != TipoRol.EMPLEADO) {
            throw new NoConformidadExceptions("No tiene permisos para crear el punto de no conformidad");
        }
        if (nuevoPuntoNcDTO.getNuevoPuntoNC() == null || nuevoPuntoNcDTO.getIdNoConformidad() == null || nuevoPuntoNcDTO.getIdProyecto() == null) {
            throw new CrearPuntoNoConformidadExceptions("Datos incompletos para crear el punto de no conformidad");
        }
        return noConformidadService.crearPuntoNoConformidad(nuevoPuntoNcDTO) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Crear no conformidad")
    @PostMapping("/crearNoConformidad")
    public ResponseEntity<?> crearNoConformidad(@RequestBody CrearNoConformidadDto crearNoConformidadDto) {
        Usuario usuario = getAuthenticatedUser();
        if (usuario.getRol().getTipoRol() != TipoRol.EMPLEADO) {
            throw new NoConformidadExceptions("No tiene permisos para crear la no conformidad");
        }
        return (crearNoConformidadDto.getIdProyecto() == null || crearNoConformidadDto.getTipo() == null) ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.status(noConformidadService.crearNoConformidad(crearNoConformidadDto) ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }
}