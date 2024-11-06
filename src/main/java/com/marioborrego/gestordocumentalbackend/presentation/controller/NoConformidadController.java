package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public List<NoConformidad> verNcsProyecto() {
        return noConformidadService.obtenerNoConformiddadProyecto("1");
    }
}
