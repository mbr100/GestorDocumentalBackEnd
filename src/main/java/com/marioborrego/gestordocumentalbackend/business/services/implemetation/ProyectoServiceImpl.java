package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.CarpetaService;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.EmpleadoService;
import com.marioborrego.gestordocumentalbackend.business.utils.CodeProyect;
import com.marioborrego.gestordocumentalbackend.domain.models.Empleado;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyectos;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.EmpleadoProyectoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.CrearProyectoDTO;
import com.marioborrego.gestordocumentalbackend.domain.repositories.ProyectosRepository;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.ProyectoService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.ListarProyectoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProyectoServiceImpl implements ProyectoService {
    private final ProyectosRepository proyectosRepository;
    private final CarpetaService carpetaService;
    private final EmpleadoService empleadoService;
    private static final Logger logger = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    public ProyectoServiceImpl(ProyectosRepository proyectosRepository, CarpetaService carpetaService, EmpleadoService empleadoService ) {
        this.proyectosRepository = proyectosRepository;
        this.carpetaService = carpetaService;
        this.empleadoService = empleadoService;
    }

    @Override
    @Transactional
    public Map<String, String> crearProyecto(CrearProyectoDTO proyecto) {
        Map<String, String> respuesta = new HashMap<>();
        boolean existeProyecto = proyectosRepository.findbytitulo(proyecto.getTitulo());
        Empleado empleado = empleadoService.getEmpleadoById(proyecto.getIdEmpleado());
        if (existeProyecto) {
            respuesta.put("status", "error");
            respuesta.put("message", "El proyecto ya existe");
            return respuesta;
        }
        if (empleado == null) {
            respuesta.put("status", "error");
            respuesta.put("message", "El empleado no existe");
            return respuesta;
        }

        if (!empleado.getRol().getRol().equals("Comercial") && !empleado.getRol().getRol().equals("Administrador")) {
            respuesta.put("status", "error");
            respuesta.put("message", "El empleado no tiene permisos para crear proyectos");
            return respuesta;
        }
        try {
            Set<Empleado> emplados = new HashSet<>();
            emplados.add(empleado);
            Proyectos nuevoProyecto = Proyectos.builder()
                    .titulo(proyecto.getTitulo())
                    .ano(proyecto.getAno())
                    .cliente(proyecto.getCliente())
                    .empleados(emplados)
                    .build();
            Proyectos proyectoGuardado = proyectosRepository.save(nuevoProyecto);
            carpetaService.createProjectDirectory(proyectoGuardado.getCodigo());
            respuesta.put("status", "success");
            respuesta.put("message", "Proyecto creado con exito");
        } catch (Exception e) {
            respuesta.put("status", "error");
            respuesta.put("message", "Error al crear el proyecto");
        }
        return respuesta;
    }

    @Override
    public List<ListarProyectoDTO> getAllProyectos() {
        try {
            return proyectosRepository.findAll().stream().map(proyecto -> {
                List<EmpleadoProyectoDTO> empleadosProyecto = new ArrayList<>();
                proyecto.getEmpleados().stream().peek(empleado -> empleadosProyecto.add(EmpleadoProyectoDTO.builder()
                        .nombre(empleado.getNombre())
                        .email(empleado.getEmail())
                        .telefono(empleado.getTelefono())
                        .rol(empleado.getRol().getRol())
                        .build())).toList();
                return ListarProyectoDTO.builder()
                        .idProyecto(CodeProyect.idToCodeProyect(proyecto.getCodigo()))
                        .titulo(proyecto.getTitulo())
                        .ano(proyecto.getAno())
                        .cliente(proyecto.getCliente())
                        .empleadoProyecto(empleadosProyecto)
                        .build();
            }).toList();

        } catch (Exception e) {
            logger.error("Error al obtener los proyectos", e);
            return List.of();
        }
    }

    @Override
    public Map<String, String> actualizarProyecto(ListarProyectoDTO proyecto) {
        Map<String, String> respuesta = new HashMap<>();
        Proyectos proyectoaActualizar = proyectosRepository.findByCodigo(CodeProyect.codeProyectToId(proyecto.getIdProyecto()));
        if (proyectoaActualizar == null) {
            respuesta.put("status", "error");
            respuesta.put("message", "El proyecto no existe");
            return respuesta;
        }
        try {
            Set<Empleado> empleadosProyecto = new HashSet<>();
            proyecto.getEmpleadoProyecto().forEach(empleado -> {
                logger.info("Empleado: {}", empleado.getNombre());
                Empleado empleadoProyecto = empleadoService.getEmpleadoByNombre(empleado.getNombre());

                empleadosProyecto.add(empleadoProyecto); // Agrega al Set
            });

            proyectoaActualizar.setTitulo(proyecto.getTitulo());
            proyectoaActualizar.setAno(proyecto.getAno());
            proyectoaActualizar.setCliente(proyecto.getCliente());
            proyectoaActualizar.setEmpleados(empleadosProyecto);
            proyectosRepository.save(proyectoaActualizar);
            respuesta.put("status", "success");
            respuesta.put("message", "Proyecto actualizado con exito");
        } catch (Exception e) {
            logger.error("Error al actualizar el proyecto", e);
            respuesta.put("status", "error");
            respuesta.put("message", "Error al actualizar el proyecto");
        }
        return respuesta;
    }
}
