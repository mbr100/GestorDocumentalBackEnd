package com.marioborrego.gestordocumentalbackend.services.implemetation;

import com.marioborrego.gestordocumentalbackend.models.Empleado;
import com.marioborrego.gestordocumentalbackend.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.CrearEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.repositories.EmpleadoRepository;
import com.marioborrego.gestordocumentalbackend.services.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmpleadosServiceImpl implements EmpleadoService {
    private static final Logger log = LoggerFactory.getLogger(EmpleadosServiceImpl.class);
    private final EmpleadoRepository empleadoRepository;

    public EmpleadosServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> getEmpleadosPorRol() {
        return null;
    }

    @Override
    public List<ListarEmpleadoDTO> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados.stream()
                .map(empleado -> ListarEmpleadoDTO.builder()
                        .idEmpleado(empleado.getIdEmpleado())
                        .nombre(empleado.getNombre())
                        .email(empleado.getEmail())
                        .telefono(empleado.getTelefono())
                        .rol(empleado.getRol().getRol())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> eliminarEmpleado(int id) {
        Map<String, String> response = new HashMap<>();
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        if (empleado != null) {
            if (empleado.getRol().getRol().equals("admin")) {
                response.put("mensaje", "No se puede eliminar un empleado con rol de administrador");
                response.put("status", "error");
                return response;
            }
            if (empleadoRepository.existsProyectosByEmpleadoId(id)) {
                response.put("status", "error");
                response.put("mensaje", "No se puede eliminar un empleado con proyectos asociados");
                return response;
            }
            try {
                empleadoRepository.deleteById(id);
                response.put("status", "success");
                response.put("mensaje", "Empleado eliminado correctamente");
            } catch (Exception e) {
                response.put("status", "error");
                response.put("mensaje", e.getMessage());
            }
        }
        return response;
    }

    @Override
    public Map<String,String> crearEmpleado(CrearEmpleadoDTO empleadoDTO, Rol rol) {
        Map<String,String> response = new HashMap<>();
        try {
            Empleado e = empleadoRepository.findByNombre(empleadoDTO.getNombre());
            if (e ==null){
                response.put("status","error");
                response.put("message","Empleado ya existe");
            }
            if (e!=null){
                Empleado empleadoGuardar = Empleado.builder()
                        .nombre(empleadoDTO.getNombre())
                        .email(empleadoDTO.getEmail())
                        .telefono(empleadoDTO.getTelefono())
                        .rol(rol)
                        .build();
                empleadoRepository.save(empleadoGuardar);
                response.put("status","success");
                response.put("message","Empleado creado correctamente");
                log.info("Empleado creado con nombre {}", empleadoDTO.getNombre());
            }
        } catch (Error error){
            log.error("Error al crear el empleado con nombre {}", empleadoDTO.getNombre(), error);
            response.put("status","error");
            response.put("message", "Error Interno");
            return response;
        }
        return response;
    }

    @Override
    public Map<String, String> actualizarEmpleado(int id, CrearEmpleadoDTO empleadoDTO, Rol rolEmpleado) {
        Map<String, String> response = new HashMap<>();
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        try {
            if (empleado != null) {
                empleado.setNombre(empleadoDTO.getNombre());
                empleado.setEmail(empleadoDTO.getEmail());
                empleado.setTelefono(empleadoDTO.getTelefono());
                empleado.setRol(rolEmpleado);
                empleadoRepository.save(empleado);
                response.put("status", "success");
                response.put("mensaje", "Empleado actualizado correctamente");
            } else {
                response.put("status", "error");
                response.put("mensaje", "Empleado no encontrado");
            }
        } catch (Exception e) {
            log.error("Error al actualizar el empleado con id {}", id, e);
            response.put("status", "error");
            response.put("mensaje", "Error al actualizar el empleado");
        }
        return response;
    }
}
