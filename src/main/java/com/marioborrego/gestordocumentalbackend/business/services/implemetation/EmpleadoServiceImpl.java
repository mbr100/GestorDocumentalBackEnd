package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.domain.models.Empleado;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyectos;
import com.marioborrego.gestordocumentalbackend.domain.models.Rol;
import com.marioborrego.gestordocumentalbackend.domain.repositories.RolRepository;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.EditarEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.domain.repositories.EmpleadoRepository;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.EmpleadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    private static final Logger log = LoggerFactory.getLogger(EmpleadoServiceImpl.class);
    private final EmpleadoRepository empleadoRepository;
    private final RolRepository rolRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, RolRepository rolRepository) {
        this.empleadoRepository = empleadoRepository;
        this.rolRepository = rolRepository;
    }


    @Override
    public Empleado getEmpleadoById(int id) {
        return empleadoRepository.findById(id).orElse(null);
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
                response.put("message", "No se puede eliminar un empleado con rol de administrador");
                response.put("status", "error");
                return response;
            }
            if (empleadoRepository.existsProyectosByEmpleadoId(id)) {
                response.put("status", "error");
                response.put("message", "No se puede eliminar un empleado con proyectos asociados");
                return response;
            }
            try {
                empleadoRepository.deleteById(id);
                response.put("status", "success");
                response.put("message", "Empleado eliminado correctamente");
            } catch (Exception e) {
                response.put("status", "error");
                response.put("message", e.getMessage());
            }
        }
        return response;
    }

    @Override
    public Map<String,String> crearEmpleado(EditarEmpleadoDTO empleadoDTO) {
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
                        .rol(rolRepository.findByRol(empleadoDTO.getRol()))
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
    public Map<String, String> actualizarEmpleado(int id, EditarEmpleadoDTO empleadoDTO) {
        Map<String, String> response = new HashMap<>();
        Empleado empleado = empleadoRepository.findById(id).orElse(null);
        Rol rolEmpleado = rolRepository.findByRol(empleadoDTO.getRol());
        try {
            if (empleado != null) {
                empleado.setNombre(empleadoDTO.getNombre());
                empleado.setEmail(empleadoDTO.getEmail());
                empleado.setTelefono(empleadoDTO.getTelefono());
                empleado.setRol(rolEmpleado);
                empleadoRepository.save(empleado);
                response.put("status", "success");
                response.put("message", "Empleado actualizado correctamente");
            } else {
                response.put("status", "error");
                response.put("message", "Empleado no encontrado");
            }
        } catch (Exception e) {
            log.error("Error al actualizar el empleado con id {}", id, e);
            response.put("status", "error");
            response.put("message", "Error al actualizar el empleado");
        }
        return response;
    }

    @Override
    public Empleado getEmpleadoByNombre(String nombre) {
        return empleadoRepository.findByNombre(nombre);
    }

    @Override
    public List<Proyectos> getProyectosEmpleado(int idEmpleado) {
        return empleadoRepository.getProyectosEmpleado(idEmpleado);
    }
}
