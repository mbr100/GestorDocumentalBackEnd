package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.domain.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO.*;
import com.marioborrego.gestordocumentalbackend.presentation.dto.responseDTO.ResponseDTO;
import com.marioborrego.gestordocumentalbackend.domain.repositories.RolRepository;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.UsuarioService;
import com.marioborrego.gestordocumentalbackend.business.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de Usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    private final RolRepository rolRepository;

    public UsuarioController(UsuarioService usuarioService, RolRepository rolRepository) {
        this.usuarioService = usuarioService;
        this.rolRepository = rolRepository;
    }

    @Operation(summary = "Obtener todos los Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron empleados"),
            @ApiResponse(responseCode = "500", description = "Fallo al recuperar los usuarios")
    })
    @GetMapping()
    public ResponseEntity<List<ListarUsuariosDTO>> obtenerTodosLosUsuarios() {
        try {
            List<ListarUsuariosDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuarios);
            }
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Eliminar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar el usuario")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> eliminarUsuario(@PathVariable int id) {
        try {
            boolean resultado = usuarioService.eliminarUsuario(id);
            HttpStatus httpStatus = resultado ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
            String status = resultado ? "success" : "error";
            String message = resultado ? "Usuario eliminado correctamente" : "Error al eliminar el usuario";
            return ResponseEntity.status(httpStatus).body(new ResponseDTO(status, message));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("error", "Error al eliminar el usuario"));
        }
    }

    @Operation(summary = "Crear un Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al crear el usuario")
    })
    @PostMapping
    public ResponseEntity<ResponseDTO> crearUsuario(@RequestBody CrearUsuarioDTO usuarioDTO) {
        try {
            if (usuarioDTO.getRol() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO("error", "El rol no puede ser nulo"));
            }

            if (usuarioDTO.getNombre() == null || TelefonoValidator.isTelefonoInValid(usuarioDTO.getTelefono()) || EmailValidator.isEmailInValid(usuarioDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("error", "Datos incorrectos"));
            }

            boolean crearEmpleado = usuarioService.crearUsuario(usuarioDTO);
            if (crearEmpleado){
                return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("success", "Empleado creado correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("error", "Error al crear el empleado"));
            }

        } catch (Exception e) {
            logger.error("Error al crear el empleado con nombre {}", usuarioDTO.getNombre(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO("error", "Error al crear el empleado"));
        }
    }

    @Operation(summary = "Actualizar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al actualizar el usuario")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> actualizarUsuario(@PathVariable int id, @RequestBody EditarUsuarioDTO usuarioDTO) {
        try {
            Rol rolUsuario = rolRepository.findByRol(usuarioDTO.getRol());
            if (rolUsuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO("error", "Rol no encontrado"));
            }

            if (usuarioDTO.getNombre() == null || TelefonoValidator.isTelefonoInValid(usuarioDTO.getTelefono()) || EmailValidator.isEmailInValid(usuarioDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("error", "Datos incorrectos"));
            }

            boolean actualizado = usuarioService.actualizarUsuario(id, usuarioDTO);
            if (!actualizado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO("error", "Usuario no encontrado"));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("success", "Usuario actualizado correctamente"));

        } catch (Exception e) {
            logger.error("Error al actualizar el empleado con nombre {}", usuarioDTO.getNombre(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("error", "Error al actualizar el empleado"));
        }
    }
}
