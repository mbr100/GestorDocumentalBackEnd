package com.marioborrego.gestordocumentalbackend.services;

import com.marioborrego.gestordocumentalbackend.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.RolDTO.EditarRolDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.RolDTO.RolDTO;

import java.util.List;

public interface RolService {
    List<RolDTO> obtenerTodosLosRolesConEmpleados();
    List<Rol> obtenerTodosLosRoles();
    boolean eliminarRol(String Rol);
    boolean existeRol(String Rol);
    boolean rolTieneEmpleados(String Rol);
    boolean actualizarRol(EditarRolDTO Rol);
    boolean crearRol(String Rol);
}
