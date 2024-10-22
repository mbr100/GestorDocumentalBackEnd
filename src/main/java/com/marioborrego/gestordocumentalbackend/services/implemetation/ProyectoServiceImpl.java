package com.marioborrego.gestordocumentalbackend.services.implemetation;

import com.marioborrego.gestordocumentalbackend.models.Proyectos;
import com.marioborrego.gestordocumentalbackend.presentation.dto.CrearProyectoDTO;
import com.marioborrego.gestordocumentalbackend.repositories.ProyectosRepository;
import com.marioborrego.gestordocumentalbackend.services.ProyectoService;
import org.springframework.stereotype.Service;

@Service
public class ProyectoServiceImpl implements ProyectoService {
    private final ProyectosRepository proyectosRepository;


    public ProyectoServiceImpl(ProyectosRepository proyectosRepository) {
        this.proyectosRepository = proyectosRepository;
    }

    @Override
    public int crearProyecto(CrearProyectoDTO proyecto) {
        Proyectos nuevoProyecto = Proyectos.builder()
                .codigo(proyecto.getCodigo())
                .titulo(proyecto.getTitulo())
                .ano(proyecto.getAno())
                .cliente(proyecto.getCliente())
                .build();
        return 0;
    }
}
