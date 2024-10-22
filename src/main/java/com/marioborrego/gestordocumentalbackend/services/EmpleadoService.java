package com.marioborrego.gestordocumentalbackend.services;

import com.marioborrego.gestordocumentalbackend.models.Empleado;
import com.marioborrego.gestordocumentalbackend.presentation.dto.EmpleadoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmpleadoService {
    List<Empleado> getEmpleadosPorRol();
    List<EmpleadoDTO> obtenerTodosLosEmpleados();
}
