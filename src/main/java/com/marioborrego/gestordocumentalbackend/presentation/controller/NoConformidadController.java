package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.NoConformidadesProyectoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/ncs")
public class NoConformidadController {
    private final NoConformidadService noConformidadService;

    public NoConformidadController(NoConformidadService noConformidadService) {
        this.noConformidadService = noConformidadService;
    }

    @GetMapping("/proyecto/:idProyecto")
    public List<NoConformidadesProyectoDto> verNcsProyecto(@PathVariable String id) {
        return noConformidadService.noConformidadesPorProyecto(id).stream().map(NoConformidadesProyectoDto::new).toList();
    }
}
