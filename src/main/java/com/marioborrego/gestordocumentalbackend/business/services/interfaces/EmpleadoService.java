package com.marioborrego.gestordocumentalbackend.business.services.interfaces;

import com.marioborrego.gestordocumentalbackend.domain.models.Empleado;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.EditarEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface EmpleadoService {
    Empleado getEmpleadoById(int id);
    List<ListarEmpleadoDTO> obtenerTodosLosEmpleados();
    Map<String,String> eliminarEmpleado(int id);
    Map<String,String> crearEmpleado(EditarEmpleadoDTO empleadoDTO);
    Map<String, String> actualizarEmpleado(int id, EditarEmpleadoDTO empleadoDTO);
    Empleado getEmpleadoByNombre(String nombre);
}
