package com.marioborrego.gestordocumentalbackend.services;

import com.marioborrego.gestordocumentalbackend.presentation.dto.CrearProyectoDTO;

public interface ProyectoService {
    int crearProyecto(CrearProyectoDTO proyecto);
}
