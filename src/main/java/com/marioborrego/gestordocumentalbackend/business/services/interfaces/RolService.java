package com.marioborrego.gestordocumentalbackend.business.services.interfaces;

import com.marioborrego.gestordocumentalbackend.domain.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.EditarRolDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.RolDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RolService {
    List<RolDTO> obtenerTodosLosRolesConEmpleados();
    List<Rol> obtenerTodosLosRoles();
    Map<String,String> eliminarRol(String Rol);
    Map<String, String> actualizarRol(EditarRolDTO Rol);
    Map<String, String> crearRol(String Rol);
    List<Rol> getAllRolesProyectos();
}
