package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.domain.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.EditarRolDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.RolDTO;
import com.marioborrego.gestordocumentalbackend.domain.repositories.RolRepository;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.RolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolService {
    private final Logger logger = LoggerFactory.getLogger(RolServiceImpl.class);
    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<RolDTO> obtenerTodosLosRolesConEmpleados() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream().map(rol -> new RolDTO(
                rol.getRol(),
                rol.getEmpleados().stream()
                        .map(empleado -> new ListarEmpleadoDTO(
                                empleado.getIdEmpleado(),
                                empleado.getNombre(),
                                empleado.getEmail(),
                                empleado.getTelefono(),
                                empleado.getRol().getRol()
                        ))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    @Override
    public List<Rol> obtenerTodosLosRoles() {
        return rolRepository.findRol();
    }

    @Override
    public Map<String,String> eliminarRol(String Rol) {
        Map<String, String> response = new HashMap<>();
        try {
            Rol rolEliminar = rolRepository.findByRol(Rol);
            if (rolEliminar == null) {
                response.put("status", "error");
                response.put("message", "El rol no existe");
                return response;
            }
            if (rolRepository.contarEmpleadosPorRol(Rol) > 0) {
                response.put("status", "error");
                response.put("message", "No se puede eliminar un rol con empleados asociados");
                return response;
            }
            rolRepository.delete(rolEliminar);
            response.put("status", "success");
            response.put("message", "Rol eliminado correctamente");
            return response;
        } catch (Exception e) {
            logger.error("Error al eliminar el rol {}", Rol);
            logger.error(e.getMessage());
            response.put("status", "error");
            response.put("message", e.getMessage());
            return response;
        }
    }

    @Override
    @Transactional
    public Map<String, String> actualizarRol(EditarRolDTO Rol) {
        Map<String, String> response = new HashMap<>();
        try {
            if (rolRepository.findByRol(Rol.getAntiuguoRol()) == null) {
                response.put("status", "error");
                response.put("message", "No existe el rol a actualizar");
                return response;
            }
            if (rolRepository.findByRol(Rol.getNuevoRol()) != null) {
                response.put("status", "error");
                response.put("message", "El nuevo rol ya existe");
                return response;
            }
            rolRepository.actualizarRol(Rol.getAntiuguoRol(), Rol.getNuevoRol());
            response.put("status", "success");
            response.put("message", "Rol actualizado correctamente");
            return response;
        } catch (Exception e) {
            logger.error("Error al actualizar el rol {}", Rol.getAntiuguoRol());
            logger.error(e.getMessage());
            response.put("status", "error");
            response.put("message", e.getMessage());
            return response;
        }
    }

    @Override
    public  Map<String, String> crearRol(String Rol) {
        Map<String, String> response = new HashMap<>();
        if (rolRepository.findByRol(Rol) != null) {
            response.put("status", "error");
            response.put("message", "El rol ya existe");
        }
        Rol nuevoRol = new Rol();
        nuevoRol.setRol(Rol);
        rolRepository.save(nuevoRol);
        response.put("status", "success");
        response.put("message", "Rol creado correctamente");
        return response;
    }

    @Override
    public List<Rol> getAllRolesProyectos() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream().filter( rol -> !"administrador".equals(rol.getRol())).collect(Collectors.toList());
    }
}