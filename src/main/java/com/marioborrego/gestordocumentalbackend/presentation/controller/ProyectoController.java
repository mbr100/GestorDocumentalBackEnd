package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.CrearProyectoDTO;
import com.marioborrego.gestordocumentalbackend.services.ProyectoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/proyectos")
public class ProyectoController {
    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }


    @PostMapping()
    public void crearProyecto(@RequestBody CrearProyectoDTO proyecto) {

    }
}
