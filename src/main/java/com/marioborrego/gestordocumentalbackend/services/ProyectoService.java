package com.marioborrego.gestordocumentalbackend.services;

import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.CrearProyectoDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProyectoService {
    int crearProyecto(CrearProyectoDTO proyecto);
}
