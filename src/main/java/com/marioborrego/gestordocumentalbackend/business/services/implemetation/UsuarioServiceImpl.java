package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.exceptions.*;

import com.marioborrego.gestordocumentalbackend.domain.models.*;
import com.marioborrego.gestordocumentalbackend.domain.repositories.RolRepository;
import com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO.*;
import com.marioborrego.gestordocumentalbackend.domain.repositories.UsuarioRepository;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario getUsuarioById(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<ListarUsuariosDTO> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findUsuarioByActivo();
        return usuarios.stream()
                .map(empleado -> ListarUsuariosDTO.builder()
                        .idUsuario(empleado.getIdUsuario())
                        .nombre(empleado.getNombre())
                        .email(empleado.getEmail())
                        .telefono(empleado.getTelefono())
                        .rol(empleado.getRol().getRol())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean eliminarUsuario(int id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            if (usuario.getRol().getRol().equals("admin")) {
                throw new EliminarUsuarioExceptions("No se puede eliminar un usuario administrador");
            }
            if (usuarioRepository.existsProyectosByEmpleadoId(id)) {
                throw new EliminarUsuarioExceptions("No se puede eliminar un usuario asignado a un proyecto");
            }
            try {
                usuario.setActivo(false);
                usuarioRepository.save(usuario);
                return true;
            } catch (Exception e) {
                log.error("Error al eliminar el empleado con id {}", id, e);
                throw new InternalServerError("Error al eliminar el empleado");
            }
        } else {
            throw new EliminarUsuarioExceptions("Empleado no encontrado");
        }
    }

    @Override
    public boolean crearUsuario(CrearUsuarioDTO usuarioDTO) {
        try {
            Usuario e = usuarioRepository.findByNombre(usuarioDTO.getNombre());
            if (e != null) {
                throw new CrearUsuarioExceptions("El empleado ya existe");
            }
            Usuario usuarioGuardar = Usuario.builder()
                    .nombre(usuarioDTO.getNombre())
                    .email(usuarioDTO.getEmail())
                    .telefono(usuarioDTO.getTelefono())
                    .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                    .rol(rolRepository.findByRol(usuarioDTO.getRol()))
                    .activo(true)
                    .build();
            usuarioRepository.save(usuarioGuardar);
            return true;
        } catch (Error error) {
            log.error("Error al crear el empleado con nombre {}", usuarioDTO.getNombre(), error);
            throw new InternalServerError("Error al crear el empleado");
        }
    }

    @Override
    public boolean actualizarUsuario(int id, EditarUsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new ActualizarUsuarioExceptions("Empleado no encontrado");
        }
        Rol rolEmpleado = rolRepository.findByRol(usuarioDTO.getRol());
        if (rolEmpleado == null) {
            throw new ActualizarUsuarioExceptions("Rol no encontrado");
        }
        try {
            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setTelefono(usuarioDTO.getTelefono());
            usuario.setRol(rolEmpleado);
            usuarioRepository.save(usuario);
            return true;
        } catch (Exception e) {
            log.error("Error al actualizar el empleado con id {}", id, e);
            throw new InternalServerError("Error al actualizar el empleado");
        }
    }

    @Override
    public Usuario getUsuarioByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    public List<Proyecto> getProyectosUsuario(int idEmpleado) {
        return usuarioRepository.getProyectosEmpleado(idEmpleado);
    }
}
