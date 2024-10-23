package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.EditarRolDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.RolDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO.RolesDTO;
import com.marioborrego.gestordocumentalbackend.services.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("api/roles")
@Tag(name = "Roles", description = "Gestión de roles")
public class RolController {
    private final RolService rolService;
    private final Logger logger = LoggerFactory.getLogger(RolController.class);

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @Operation(summary = "Obtener todos los roles con sus empleados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles con empleados obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron roles con empleados"),
            @ApiResponse(responseCode = "500", description = "Fallo al recuperar los roles con empleados")
    })
    @GetMapping("obtenerEmpleadosPorRol")
    public ResponseEntity<List<RolDTO>> obtenerTodosLosRolesConEmpleados() {
        try {
             List<RolDTO> rolesConEmpleados = rolService.obtenerTodosLosRolesConEmpleados();
             if (rolesConEmpleados.isEmpty()) {
                 return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rolesConEmpleados);
             }
             return ResponseEntity.status(HttpStatus.OK).body(rolesConEmpleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener todos los roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron roles"),
            @ApiResponse(responseCode = "500", description = "Fallo al recuperar los roles")
    })
    @GetMapping("obtenerRoles")
    public ResponseEntity<List<RolesDTO>> obtenerTodosLosRoles() {
        try {
            List<Rol> roles = rolService.obtenerTodosLosRoles();
            List<RolesDTO> rolesDTOS = roles.stream().map(rol -> new RolesDTO(
                    rol.getRol()
            )).toList();
            if (rolesDTOS.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(rolesDTOS);
            }
            return ResponseEntity.status(HttpStatus.OK).body(rolesDTOS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Eliminar un rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontró el rol a eliminar"),
            @ApiResponse(responseCode = "500", description = "Fallo al eliminar el rol")
    })
    @DeleteMapping("eliminarRol/{rol}")
    public ResponseEntity<Map<String,String>> eliminarRol(@PathVariable String rol) {
        Map<String,String> mensaje = new HashMap<>();
        //Comprobar si el rol existe
        if (!rolService.existeRol(rol)) {
            mensaje.put("message", "No existe Rol a eliminar");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mensaje);
        }
        //Comprobar si el rol tiene empleados asignados
        if (rolService.rolTieneEmpleados(rol)) {
            mensaje.put("message", "El rol tiene empleados asignados");
            logger.error("El rol {} tiene empleados asignados", rol);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
        try {
            boolean eliminado = rolService.eliminarRol(rol);
            if (!eliminado) {
                mensaje.put("message", "Error al eliminar el rol");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mensaje);
            }
            mensaje.put("message", "Rol eliminado correctamente");
            mensaje.put("code", String.valueOf(HttpStatus.OK.value()));
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Editar un rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "No existe el rol a actualizar"),
            @ApiResponse(responseCode = "500", description = "Fallo al actualizar el rol")
    })
    @PutMapping("editarRol")
    public ResponseEntity<Map<String, String>> actualizarRol(@RequestBody EditarRolDTO rol) {
        logger.info("Editar rol {}", rol.antiuguoRol);
        Map<String,String> mensaje = new HashMap<>();
        if (!rolService.existeRol(rol.antiuguoRol)) {
            mensaje.put("message", "No existe Rol a actualizar");
            logger.error("respuesta: {}", mensaje);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
        if (rolService.existeRol(rol.nuevoRol)) {
            mensaje.put("message", "El rol ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
        try {
            boolean actualizado = rolService.actualizarRol(rol);
            if (!actualizado) {
                mensaje.put("message", "Error al actualizar el rol");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
            }
            mensaje.put("message", "Rol actualizado correctamente");
            mensaje.put("code", String.valueOf(HttpStatus.OK.value()));
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (Exception e) {
            mensaje.put("message", "Error al actualizar el rol");
            mensaje.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }
    }

    @Operation(summary = "Crear un rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "El rol ya existe"),
            @ApiResponse(responseCode = "500", description = "Fallo al crear el rol")
    })
    @PostMapping("crearRol")
    public ResponseEntity<Map<String, String>> crearRol(@RequestBody RolesDTO rol) {
        Map<String,String> mensaje = new HashMap<>();
        if (rolService.existeRol(rol.getRol())) {
            mensaje.put("message", "El rol ya existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
        try {
            boolean creado = rolService.crearRol(rol.getRol());
            if (!creado) {
                mensaje.put("message", "Error al crear el rol");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
            }
            mensaje.put("message", "Rol creado correctamente");
            mensaje.put("code", String.valueOf(HttpStatus.CREATED.value()));
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (Exception e) {
            mensaje.put("message", "Error al crear el rol");
            mensaje.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensaje);
        }
    }
}