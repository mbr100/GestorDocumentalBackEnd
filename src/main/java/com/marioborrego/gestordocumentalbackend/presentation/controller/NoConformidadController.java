package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.ProyectoService;
import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.CrearPuntoNoConformidadExceptions;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.CrearNoConformidadDto;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.NoConformidadesProyectoDto;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.NuevoPuntoNcDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.RespuestaPuntoNoConformidad;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.NcNoEncontradaExceptions;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.NoConformidadExceptions;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.UsuarioNoExisteExceptions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Operation(summary = "Ver no conformidades de un proyecto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No conformidades obtenidas correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron no conformidades"),
            @ApiResponse(responseCode = "404", description = "Proyecto no encontrado")
    })
    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<NoConformidadesProyectoDto>> verNcsProyecto(@PathVariable() String idProyecto) {
        if (!proyectoService.existeProyecto(idProyecto)) {
            return ResponseEntity.notFound().build();
        }
        if (noConformidadService.noConformidadesPorProyecto(idProyecto).isEmpty()) {
            throw new NcNoEncontradaExceptions("No se encontraron no conformidades");
        }
        return ResponseEntity.ok(noConformidadService.noConformidadesPorProyecto(idProyecto).stream().map(NoConformidadesProyectoDto::new).toList());
    }

    @Operation(summary = "Responder no conformidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No conformidad respondida correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo responder la no conformidad")
    })
    @PostMapping("/responder")
    public ResponseEntity<?> responderNoConformidad(@RequestBody @Valid RespuestaPuntoNoConformidad respuestaNoConformidadesProyectoDto) {
        if (respuestaNoConformidadesProyectoDto.getContenido() == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean puntoRespondido = noConformidadService.responderNoConformidad(respuestaNoConformidadesProyectoDto);
        return ResponseEntity.status(puntoRespondido ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

//    @Operation(summary = "Cerrar punto no conformidad")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Punto no conformidad cerrado correctamente"),
//            @ApiResponse(responseCode = "400", description = "No se pudo cerrar el punto no conformidad")
//    })
//    @GetMapping("/cerrarPuntoNc/{idPuntoNc}")
//    public ResponseEntity<?> cerrarPuntoNc(@PathVariable() int idPuntoNc) {
//        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (usuario == null) {
//            throw new UsuarioNoExisteExceptions("No Conformidad no encontrada");
//        }
//        if (usuario.getRol().getTipoRol() != TipoRol.EMPLEADO) {
//            throw new NoConformidadExceptions("No tiene permisos para cerrar la no conformidad");
//        }
//        if (noConformidadService.cerrarPuntoNc((long) idPuntoNc)) {
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @PutMapping("/cerrarPuntoNc/{idPuntoNc}")
    public ResponseEntity<?> cerrarPuntoNc(@PathVariable() int idPuntoNc) {
        log.info("Respuesta: {}", idPuntoNc);
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (usuario == null) {
            log.error("Usuario no encontrado");
            throw new UsuarioNoExisteExceptions("No Conformidad no encontrada");
        }
        if (usuario.getRol().getTipoRol() != TipoRol.EMPLEADO) {
            log.error("No tiene permisos para cerrar la no conformidad");
            throw new NoConformidadExceptions("No tiene permisos para cerrar la no conformidad");
        }
        if (noConformidadService.cerrarPuntoNc((long) idPuntoNc)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @Operation(summary = "Crear punto no conformidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Punto no conformidad creado correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo crear el punto no conformidad")
    })
    @PostMapping("/crearPuntoNoConformidad")
    public ResponseEntity<?> crearPuntoNoConformidad(@RequestBody NuevoPuntoNcDTO nuevoPuntoNcDTO) {
        log.info("Respuesta: {}", nuevoPuntoNcDTO.nuevoPuntoNC);
        log.info("Respuesta: {}", nuevoPuntoNcDTO.idNoConformidad);
        log.info("Respuesta: {}", nuevoPuntoNcDTO.idProyecto);
        if (nuevoPuntoNcDTO.getNuevoPuntoNC() == null ) {
            throw new CrearPuntoNoConformidadExceptions("El punto no conformidad no puede estar vacío");
        }
        if (nuevoPuntoNcDTO.getIdNoConformidad() == null){
            throw new CrearPuntoNoConformidadExceptions("El id de la no conformidad no puede estar vacío");
        }
        if (nuevoPuntoNcDTO.getIdProyecto() == null){
            throw new CrearPuntoNoConformidadExceptions("El id del proyecto no puede estar vacío");
        }
        boolean puntoCreado = noConformidadService.crearPuntoNoConformidad(nuevoPuntoNcDTO);
        return ResponseEntity.status(puntoCreado ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

    @Operation(summary = "Crear no conformidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "No conformidad creada correctamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo crear la no conformidad")
    })
    @PostMapping("/crearNoConformidad")
    public ResponseEntity<?> crearNoConformidad(@RequestBody CrearNoConformidadDto crearNoConformidadDto) {
        if (crearNoConformidadDto.getIdProyecto() == null || crearNoConformidadDto.getTipo() == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean noConformidadCreada = noConformidadService.crearNoConformidad(crearNoConformidadDto);
        return ResponseEntity.status(noConformidadCreada ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }
}
