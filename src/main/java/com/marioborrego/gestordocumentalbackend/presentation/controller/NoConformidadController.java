package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.ProyectoService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.CrearNoConformidadDto;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.NoConformidadesProyectoDto;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.NuevoPuntoNcDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.RespuestaPuntoNoConformidad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/proyecto/{idProyecto}")
    public ResponseEntity<List<NoConformidadesProyectoDto>> verNcsProyecto(@PathVariable() String idProyecto) {
        if (!proyectoService.existeProyecto(idProyecto)) {
            return ResponseEntity.notFound().build();
        }
        if (noConformidadService.noConformidadesPorProyecto(idProyecto).isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(noConformidadService.noConformidadesPorProyecto(idProyecto).stream().map(NoConformidadesProyectoDto::new).toList());
    }

    @PostMapping("/responder")
    public ResponseEntity<?> responderNoConformidad(@RequestBody RespuestaPuntoNoConformidad respuestaNoConformidadesProyectoDto) {
        log.info("Respuesta: {}", respuestaNoConformidadesProyectoDto);
        if (respuestaNoConformidadesProyectoDto.getIdNoConformidad() == null || respuestaNoConformidadesProyectoDto.getContenido() == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean puntoRespondido = noConformidadService.responderNoConformidad(respuestaNoConformidadesProyectoDto);
        return ResponseEntity.status(puntoRespondido ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/cerrarPuntoNc/{idPuntoNc}")
    public ResponseEntity<?> cerrarPuntoNc(@PathVariable() Long idPuntoNc) {
        if (noConformidadService.cerrarPuntoNc(idPuntoNc)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/crearPuntoNoConformidad")
    public ResponseEntity<?> crearPuntoNoConformidad(@RequestBody NuevoPuntoNcDTO nuevoPuntoNcDTO) {
        log.info("Respuesta: {}", nuevoPuntoNcDTO.nuevoPuntoNC);
        log.info("Respuesta: {}", nuevoPuntoNcDTO.idNoConformidad);
        log.info("Respuesta: {}", nuevoPuntoNcDTO.idProyecto);
        if (nuevoPuntoNcDTO.getNuevoPuntoNC() == null || nuevoPuntoNcDTO.getIdNoConformidad() == null|| nuevoPuntoNcDTO.getIdProyecto() == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean puntoCreado = noConformidadService.crearPuntoNoConformidad(nuevoPuntoNcDTO);
        return ResponseEntity.status(puntoCreado ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/crearNoConformidad")
    public ResponseEntity<?> crearNoConformidad(@RequestBody CrearNoConformidadDto crearNoConformidadDto) {
        log.info("Respuesta: {}", crearNoConformidadDto);
        if (crearNoConformidadDto.getIdProyecto() == null || crearNoConformidadDto.getTipo() == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean noConformidadCreada = noConformidadService.crearNoConformidad(crearNoConformidadDto);
        return ResponseEntity.status(noConformidadCreada ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }
}
