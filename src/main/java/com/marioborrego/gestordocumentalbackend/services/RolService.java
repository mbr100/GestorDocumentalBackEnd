package com.marioborrego.gestordocumentalbackend.services;

import com.marioborrego.gestordocumentalbackend.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.EditarRolDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.RolDTO;

import java.util.List;
import java.util.Map;

public interface RolService {
    List<RolDTO> obtenerTodosLosRolesConEmpleados();
    List<Rol> obtenerTodosLosRoles();
    Map<String,String> eliminarRol(String Rol);
    boolean existeRol(String Rol);
    boolean rolTieneEmpleados(String Rol);
    Map<String, String> actualizarRol(EditarRolDTO Rol);
    Map<String, String> crearRol(String Rol);
}
