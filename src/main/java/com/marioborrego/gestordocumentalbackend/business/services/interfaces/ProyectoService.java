package com.marioborrego.gestordocumentalbackend.business.services.interfaces;

import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.CrearProyectoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.ListarProyectoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.ListarProyectoEmpleadoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProyectoService {
    Map<String,String> crearProyecto(CrearProyectoDTO proyecto);
    List<ListarProyectoDTO> getAllProyectos();
    Map<String, String> actualizarProyecto(ListarProyectoDTO proyecto);
    List<ListarProyectoEmpleadoDTO> getProyectosEmpleado(int idEmpleado);
}
