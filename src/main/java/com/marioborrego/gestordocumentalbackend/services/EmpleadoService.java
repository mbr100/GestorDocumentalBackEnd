package com.marioborrego.gestordocumentalbackend.services;

import com.marioborrego.gestordocumentalbackend.models.Empleado;
import com.marioborrego.gestordocumentalbackend.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.CrearEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmpleadoService {
    List<Empleado> getEmpleadosPorRol();
    List<ListarEmpleadoDTO> obtenerTodosLosEmpleados();
    Map<String,String> eliminarEmpleado(int id);
    Map<String,String> crearEmpleado(CrearEmpleadoDTO empleadoDTO, Rol rol);
    Map<String, String> actualizarEmpleado(int id, CrearEmpleadoDTO empleadoDTO, Rol rolEmpleado);
}
