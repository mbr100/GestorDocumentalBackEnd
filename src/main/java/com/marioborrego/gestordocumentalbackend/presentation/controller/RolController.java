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
            @ApiResponse(responseCode = "400", description = "No se puede eliminar un rol con empleados asociados"),
            @ApiResponse(responseCode = "500", description = "Fallo al eliminar el rol")
    })
    @DeleteMapping("eliminarRol/{rol}")
    public ResponseEntity<Map<String,String>> eliminarRol(@PathVariable String rol) {
        try {
            Map<String,String> message = rolService.eliminarRol(rol);
            HttpStatus status;
            if (message.get("status").equals("error")) {
                status = switch (message.get("message")) {
                    case "El rol no existe" -> HttpStatus.NO_CONTENT;
                    case "No se puede eliminar un rol con empleados asociados" -> HttpStatus.BAD_REQUEST;
                    default -> HttpStatus.INTERNAL_SERVER_ERROR;
                };
            } else {
                status = HttpStatus.OK;
            }
            return ResponseEntity.status(status).body(message);
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
        Map<String,String> message = new HashMap<>();
        HttpStatus status;
        try {
            message = rolService.actualizarRol(rol);
            if (message.get("status").equals("error")) {
                status = switch (message.get("message")) {
                    case "No existe el rol a actualizar", "El nuevo rol ya existe" -> HttpStatus.BAD_REQUEST;
                    default -> HttpStatus.INTERNAL_SERVER_ERROR;
                };
            } else{
                status = HttpStatus.OK;
            }
            return ResponseEntity.status(status).body(message);
        } catch (Exception e) {
            message.put("message", "Error al actualizar el rol");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(status).body(message);
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
        HttpStatus status;
        try {
            mensaje = rolService.crearRol(rol.getRol());
            if (mensaje.get("status").equals("error")) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                status = HttpStatus.CREATED;
            }
            return ResponseEntity.status(status).body(mensaje);
        } catch (Exception e) {
            mensaje.put("status", "error");
            mensaje.put("message", "Error al crear el rol");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(status).body(mensaje);
        }
    }
}