package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.CarpetaService;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.UsuarioService;
import com.marioborrego.gestordocumentalbackend.business.utils.CodeProyect;
import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyecto;
import com.marioborrego.gestordocumentalbackend.domain.repositories.ProyectoRepository;
import com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO.UsuarioProyectoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.CrearProyectoDTO;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.ProyectoService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.ListarProyectoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO.ListarProyectoEmpleadoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProyectoServiceImpl implements ProyectoService {
    private final ProyectoRepository proyectoRepository;
    private final CarpetaService carpetaService;
    private final UsuarioService usuarioService;
    private static final Logger logger = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    public ProyectoServiceImpl(ProyectoRepository ProyectoRepository, CarpetaService carpetaService, UsuarioService usuarioService) {
        this.proyectoRepository = ProyectoRepository;
        this.carpetaService = carpetaService;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public Map<String, String> crearProyecto(CrearProyectoDTO proyecto) {
        Map<String, String> respuesta = new HashMap<>();
        boolean existeProyecto = proyectoRepository.findbytitulo(proyecto.getTitulo());
        Usuario usuario = usuarioService.getEmpleadoByNombre(proyecto.getNombreEmpleado());
        if (existeProyecto) {
            respuesta.put("status", "error");
            respuesta.put("message", "El proyecto ya existe");
            return respuesta;
        }
        if (usuario == null) {
            respuesta.put("status", "error");
            respuesta.put("message", "El empleado no existe");
            return respuesta;
        }

        if (!usuario.getRol().getRol().equals("Comercial") && !usuario.getRol().getRol().equals("Administrador")) {
            respuesta.put("status", "error");
            respuesta.put("message", "El empleado no tiene permisos para crear proyectos");
            return respuesta;
        }
        try {
            Set<Usuario> emplados = new HashSet<>();
            emplados.add(usuario);
            Proyecto nuevoProyecto = Proyecto.builder()
                    .titulo(proyecto.getTitulo())
                    .ano(proyecto.getAno())
                    .cliente(proyecto.getCliente())
                    .usuarios(emplados)
                    .build();
            Proyecto proyectoGuardado = proyectoRepository.save(nuevoProyecto);
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
            return proyectoRepository.findAll().stream().map(proyecto -> {
                List<UsuarioProyectoDTO> empleadosProyecto = new ArrayList<>();
                proyecto.getUsuarios().stream().peek(empleado ->
                        empleadosProyecto.add(UsuarioProyectoDTO.builder()
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
        Proyecto proyectoaActualizar = proyectoRepository.findByCodigo(CodeProyect.codeProyectToId(proyecto.getIdProyecto()));
        if (proyectoaActualizar == null) {
            respuesta.put("status", "error");
            respuesta.put("message", "El proyecto no existe");
            return respuesta;
        }
        try {
            Set<Usuario> empleadosProyecto = new HashSet<>();
            proyecto.getEmpleadoProyecto().forEach(empleado -> {
                logger.info("Empleado: {}", empleado.getNombre());
                Usuario usuarioProyecto = usuarioService.getEmpleadoByNombre(empleado.getNombre());

                empleadosProyecto.add(usuarioProyecto); // Agrega al Set
            });

            proyectoaActualizar.setTitulo(proyecto.getTitulo());
            proyectoaActualizar.setAno(proyecto.getAno());
            proyectoaActualizar.setCliente(proyecto.getCliente());
            proyectoaActualizar.setUsuarios(empleadosProyecto);
            proyectoRepository.save(proyectoaActualizar);
            respuesta.put("status", "success");
            respuesta.put("message", "Proyecto actualizado con exito");
        } catch (Exception e) {
            logger.error("Error al actualizar el proyecto", e);
            respuesta.put("status", "error");
            respuesta.put("message", "Error al actualizar el proyecto");
        }
        return respuesta;
    }

    @Override
    public List<ListarProyectoEmpleadoDTO> getProyectosEmpleado(int idEmpleado) {
        try {
            return usuarioService.getProyectosEmpleado(idEmpleado).stream().map(proyecto ->
                    ListarProyectoEmpleadoDTO.builder()
                            .idProyecto(CodeProyect.idToCodeProyect(proyecto.getCodigo()))
                            .titulo(proyecto.getTitulo())
                            .ano(proyecto.getAno())
                            .cliente(proyecto.getCliente())
                            .build()).toList();
        } catch (Exception e) {
            logger.error("Error al obtener los proyectos", e);
            return List.of();
        }
    }


}
