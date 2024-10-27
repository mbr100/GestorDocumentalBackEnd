package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.domain.models.Rol;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.EditarEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.responseDTO.ResponseDTO;
import com.marioborrego.gestordocumentalbackend.domain.repositories.RolRepository;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.EmpleadoService;
import com.marioborrego.gestordocumentalbackend.business.utils.EmailValidator;
import com.marioborrego.gestordocumentalbackend.business.utils.TelefonoValidator;
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
import java.util.Map;

@RestController
@RequestMapping("api/empleados")
@Tag(name = "Empleados", description = "Gestión de empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;
    private final Logger logger = LoggerFactory.getLogger(EmpleadoController.class);
    private final RolRepository rolRepository;

    public EmpleadoController(EmpleadoService empleadoService, RolRepository rolRepository) {
        this.empleadoService = empleadoService;
        this.rolRepository = rolRepository;
    }

    @Operation(summary = "Obtener todos los empleados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados obtenidos correctamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron empleados"),
            @ApiResponse(responseCode = "500", description = "Fallo al recuperar los empleados")
    })
    @GetMapping()
    public ResponseEntity<List<ListarEmpleadoDTO>> obtenerTodosLosEmpleados() {
        try {
            List<ListarEmpleadoDTO> empleados = empleadoService.obtenerTodosLosEmpleados();
            if (empleados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(empleados);
            }
            return ResponseEntity.status(HttpStatus.OK).body(empleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Eliminar un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "409", description = "No se puede eliminar un empleado con proyectos asociados"),
            @ApiResponse(responseCode = "403", description = "No se puede eliminar un empleado con rol de administrador"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar el empleado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> eliminarEmpleado(@PathVariable int id) {
        try {
            logger.info("Eliminando empleado con id {}", id);
            Map<String, String> result = empleadoService.eliminarEmpleado(id);
            String status = result.get("status");
            String message = result.get("mensaje");

            HttpStatus httpStatus;
            switch (message) {
                case "Empleado no encontrado":
                    httpStatus = HttpStatus.NOT_FOUND;
                    break;
                case "No se puede eliminar un empleado con rol de administrador":
                    httpStatus = HttpStatus.FORBIDDEN;
                    break;
                case "No se puede eliminar un empleado con proyectos asociados":
                    httpStatus = HttpStatus.CONFLICT;
                    break;
                case "Empleado eliminado correctamente":
                    httpStatus = HttpStatus.OK;
                    break;
                default:
                    httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                    message = "Error desconocido al eliminar el empleado";
                    break;
            }
            return ResponseEntity.status(httpStatus).body(new ResponseDTO(status, message));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("error", "Error al eliminar el empleado"));
        }
    }

    @Operation(summary = "Crear un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al crear el empleado")
    })
    @PostMapping
    public ResponseEntity<ResponseDTO> crearEmpleado(@RequestBody EditarEmpleadoDTO empleadoDTO) {
        try {
            if (empleadoDTO.getRol() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO("error", "El rol no puede ser nulo"));
            }

            if (empleadoDTO.getNombre() == null || TelefonoValidator.isTelefonoInValid(empleadoDTO.getTelefono()) || EmailValidator.isEmailInValid(empleadoDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO("error", "Datos incorrectos"));
            }

            Map<String, String> result = empleadoService.crearEmpleado(empleadoDTO);
            String status = result.get("status");
            String message = result.get("mensaje");
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(status, message));

        } catch (Exception e) {
            logger.error("Error al crear el empleado con nombre {}", empleadoDTO.getNombre(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("error", "Error al crear el empleado"));
        }
    }

    @Operation(summary = "Actualizar un empleado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error al actualizar el empleado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> actualizarEmpleado(@PathVariable int id, @RequestBody EditarEmpleadoDTO empleadoDTO) {
        try {
            Rol rolEmpleado = rolRepository.findByRol(empleadoDTO.getRol());
            if (rolEmpleado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO("error", "Rol no encontrado"));
            }

            if (empleadoDTO.getNombre() == null || TelefonoValidator.isTelefonoInValid(empleadoDTO.getTelefono()) || EmailValidator.isEmailInValid(empleadoDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO("error", "Datos incorrectos"));
            }

            Map<String, String> result = empleadoService.actualizarEmpleado(id, empleadoDTO);
            String status = result.get("status");
            String message = result.get("mensaje");
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(status, message));

        } catch (Exception e) {
            logger.error("Error al actualizar el empleado con nombre {}", empleadoDTO.getNombre(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("error", "Error al actualizar el empleado"));
        }
    }
}
