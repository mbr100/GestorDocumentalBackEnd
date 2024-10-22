package com.marioborrego.gestordocumentalbackend.services.implemetation;

import com.marioborrego.gestordocumentalbackend.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.EmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.RolDTO.EditarRolDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.RolDTO.RolDTO;
import com.marioborrego.gestordocumentalbackend.repositories.RolRepository;
import com.marioborrego.gestordocumentalbackend.services.RolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
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
                        .map(empleado -> new EmpleadoDTO(
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
    public boolean eliminarRol(String Rol) {
        Rol rolEliminar = rolRepository.findByRol(Rol);
        if (rolEliminar != null) {
            try {
                rolRepository.delete(rolEliminar);
                return true;
            } catch (Exception e) {
                logger.error("Error al eliminar el rol {}", Rol);
                logger.error(e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean existeRol(String Rol) {
        return rolRepository.findByRol(Rol) != null;
    }

    @Override
    public boolean rolTieneEmpleados(String Rol) {
        return rolRepository.contarEmpleadosPorRol(Rol) > 0;
    }

    @Override
    public boolean actualizarRol(EditarRolDTO Rol) {
        try {
            Rol rolActualizar = rolRepository.findByRol(Rol.getAntiuguoRol());
            if (rolActualizar == null) {
                return false;
            }
            rolActualizar.setRol(Rol.getNuevoRol());
            rolRepository.save(rolActualizar);
            return true;
        } catch (Exception e) {
            logger.error("Error al actualizar el rol {}", Rol.getAntiuguoRol());
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean crearRol(String Rol) {
        if (rolRepository.findByRol(Rol) == null) {
            try {
                Rol nuevoRol = new Rol();
                nuevoRol.setRol(Rol);
                rolRepository.save(nuevoRol);
                return true;
            } catch (Exception e) {
                logger.error("Error al crear el rol {}", Rol);
                logger.error(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
