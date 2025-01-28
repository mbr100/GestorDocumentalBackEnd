package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.business.utils.CodeProyect;
import com.marioborrego.gestordocumentalbackend.domain.models.*;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
import com.marioborrego.gestordocumentalbackend.domain.repositories.ContenidoPuntoNoConformidadRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.NoConformidadRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.ProyectoRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.PuntosNoConformidadRepository;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.CrearNoConformidadDto;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.NuevoPuntoNcDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.RespuestaPuntoNoConformidad;
import com.marioborrego.gestordocumentalbackend.presentation.exceptions.NoConformidadExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class NoConformidadServiceImpl implements NoConformidadService {
    private final NoConformidadRepository noConformidadRepository;
    private final ProyectoRepository proyectoRepository;
    private final PuntosNoConformidadRepository puntosNoConformidadRepository;
    private final ContenidoPuntoNoConformidadRepository contenidoPuntoNoConformidadRepository;
    private final Logger log = LoggerFactory.getLogger(NoConformidadServiceImpl.class);

    public NoConformidadServiceImpl(NoConformidadRepository noConformidadRepository, PuntosNoConformidadRepository puntosNoConformidadRepository, ContenidoPuntoNoConformidadRepository contenidoPuntoNoConformidadRepository,
                                    ProyectoRepository proyectoRepository) {
        this.contenidoPuntoNoConformidadRepository = contenidoPuntoNoConformidadRepository;
        this.puntosNoConformidadRepository = puntosNoConformidadRepository;
        this.noConformidadRepository = noConformidadRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public List<NoConformidad> noConformidadesPorProyecto(String idProyecto) {
        if (idProyecto == null) {
            return List.of();
        }
        Long id = CodeProyect.decode(idProyecto);
        return noConformidadRepository.noConformidadesPorProyecto(id);
    }

    @Override
    public boolean responderNoConformidad(RespuestaPuntoNoConformidad respuestaNoConformidadesProyectoDto) {
        PuntosNoConformidad puntoNoConformidad = puntosNoConformidadRepository.findById(respuestaNoConformidadesProyectoDto.getIdNoConformidad()).orElse(null);
        if (puntoNoConformidad == null) {
            return false;
        }
        if (puntoNoConformidad.getEstado() == Estado.CERRADA){
            return false;
        }
        List<PuntosNoConformidad> puntosNC = puntoNoConformidad.getNoConformidad().getPuntosNoConformidades();
        if ((puntosNC.size() % 2 != 0 && !verificarEmpleado()) || (puntosNC.size() % 2 == 0 && verificarEmpleado())) {
            return false;
        }
        ContenidoPuntoNoConformidad contenidoPuntoNoConformidad = ContenidoPuntoNoConformidad.builder()
                .contenido(respuestaNoConformidadesProyectoDto.getContenido())
                .fecha(new Date())
                .puntosNoConformidad(puntoNoConformidad)
                .build();
        try {
            contenidoPuntoNoConformidadRepository.save(contenidoPuntoNoConformidad);
            return true;
        } catch (Exception e) {
            log.error("Error al guardar la respuesta de la no conformidad", e);
            return false;
        }
    }

    @Override
    public boolean cerrarPuntoNc(Long idPuntoNc) {
        try {
            log.info("Cerrando punto de no conformidad con id: {}", idPuntoNc);

            // Buscar el punto de no conformidad
            PuntosNoConformidad puntoNC = puntosNoConformidadRepository.findById(idPuntoNc).orElse(null);
            if (puntoNC == null || puntoNC.getEstado() == Estado.CERRADA || !verificarEmpleado()) {
                log.warn("Punto no encontrado, ya está cerrado, o usuario no tiene permisos");
                return false;
            }
            if (puntoNC.getContenidos() == null || puntoNC.getContenidos().size() == 1){
                log.warn("No se puede cerrar el punto de no conformidad porque no tiene Respuesta");
                return false;
            }

            // Validar los contenidos del punto de no conformidad
            if (puntoNC.getContenidos().size() % 2 != 0) {
                ContenidoPuntoNoConformidad contenido = ContenidoPuntoNoConformidad.builder()
                        .contenido("Cerrada")
                        .fecha(new Date())
                        .puntosNoConformidad(puntoNC)
                        .build();
                contenidoPuntoNoConformidadRepository.save(contenido);
            }

            // Cambiar el estado del punto a CERRADA
            puntoNC.setEstado(Estado.CERRADA);
            puntosNoConformidadRepository.save(puntoNC);

            // Verificar si todos los puntos de la no conformidad están cerrados
            List<PuntosNoConformidad> puntosNC = puntosNoConformidadRepository.findByNoConformidadId(puntoNC.getNoConformidad().getId());
            boolean todosCerrados = puntosNC != null && puntosNC.stream().allMatch(punto -> punto.getEstado() == Estado.CERRADA);

            if (todosCerrados) {
                // Cerrar la no conformidad
                NoConformidad noConformidad = puntoNC.getNoConformidad();
                noConformidad.setEstado(Estado.CERRADA);
                noConformidadRepository.save(noConformidad);
                log.info("No conformidad cerrada exitosamente");
            }

            return true;
        } catch (DataAccessException e) {
            log.error("Error en la base de datos al cerrar el punto de no conformidad", e);
            throw new NoConformidadExceptions("Error al cerrar el punto de no conformidad");
        } catch (Exception e) {
            log.error("Error inesperado al cerrar el punto de no conformidad", e);
            throw new RuntimeException("Error inesperado", e);
        }
    }


    @Override
    public boolean crearPuntoNoConformidad(NuevoPuntoNcDTO nuevoPuntoNcDTO) {
        try {
           if (!verificarEmpleado()) {
               return false;
           }
            ContenidoPuntoNoConformidad contenidoPuntoNoConformidad = ContenidoPuntoNoConformidad.builder()
                    .contenido(nuevoPuntoNcDTO.getNuevoPuntoNC())
                    .fecha(new Date())
                    .build();
            PuntosNoConformidad puntosNoConformidad = PuntosNoConformidad.builder()
                    .contenidos(List.of(contenidoPuntoNoConformidad))
                    .noConformidad(noConformidadRepository.findById(Long.parseLong(nuevoPuntoNcDTO.getIdNoConformidad())).orElse(null))
                    .estado(Estado.ABIERTA)
                    .build();
            puntosNoConformidadRepository.save(puntosNoConformidad);
            contenidoPuntoNoConformidad.setPuntosNoConformidad(puntosNoConformidad);
            contenidoPuntoNoConformidadRepository.save(contenidoPuntoNoConformidad);
            return true;
        } catch (Exception e) {
            log.error("Error al guardar el punto de no conformidad", e);
            return false;
        }
    }

    @Override
    public boolean crearNoConformidad(CrearNoConformidadDto crearNoConformidadDto) {
        try {
            Proyecto proyecto = proyectoRepository.findByCodigo(CodeProyect.codeProyectToId(crearNoConformidadDto.getIdProyecto()));
            if (!verificarEmpleado() || proyecto == null) {
                return false;
            }
            NoConformidad noConformidad = NoConformidad.builder()
                    .tipoNc(crearNoConformidadDto.getTipo())
                    .estado(Estado.ABIERTA)
                    .proyecto(proyecto)
                    .build();
            noConformidadRepository.save(noConformidad);
            Set<NoConformidad> NCs = proyecto.getNoConformidad();
            NCs.add(noConformidad);
            proyecto.setNoConformidad(NCs);
            proyectoRepository.save(proyecto);
            return true;
        } catch (Exception e) {
            log.error("Error al guardar la no conformidad", e);
            return false;
        }
    }

    private boolean verificarEmpleado() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuario != null && usuario.getRol().getTipoRol() != TipoRol.CLIENTE;
    }
}
