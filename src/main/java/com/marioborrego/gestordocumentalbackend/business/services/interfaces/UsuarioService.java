package com.marioborrego.gestordocumentalbackend.business.services.interfaces;

import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyecto;
import com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO.CrearUsuarioDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO.EditarUsuarioDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO.ListarUsuariosDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsuarioService {
    Usuario getUsuarioById(int id);
    List<ListarUsuariosDTO> obtenerTodosLosUsuarios();
    boolean eliminarUsuario(int id);
    boolean crearUsuario(CrearUsuarioDTO usuarioDTO);
    boolean actualizarUsuario(int id, EditarUsuarioDTO usuarioDTO);
    Usuario getUsuarioByNombre(String nombre);
    List<Proyecto> getProyectosUsuario(int idEmpleado);
    Optional<Usuario> getUsuarioByNombreONulo(String nombre);

}
