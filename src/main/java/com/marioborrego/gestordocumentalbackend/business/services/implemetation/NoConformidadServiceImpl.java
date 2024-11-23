package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.business.utils.CodeProyect;
import com.marioborrego.gestordocumentalbackend.domain.models.ContenidoPuntoNoConformidad;
import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import com.marioborrego.gestordocumentalbackend.domain.models.PuntosNoConformidad;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.repositories.ContenidoPuntoNoConformidadRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.NoConformidadRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.PuntosNoConformidadRepository;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.RespuestaPuntoNoConformidad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoConformidadServiceImpl implements NoConformidadService {
    private final NoConformidadRepository noConformidadRepository;
    private final PuntosNoConformidadRepository puntosNoConformidadRepository;
    private final ContenidoPuntoNoConformidadRepository contenidoPuntoNoConformidadRepository;
    private final Logger log = LoggerFactory.getLogger(NoConformidadServiceImpl.class);

    public NoConformidadServiceImpl(NoConformidadRepository noConformidadRepository, PuntosNoConformidadRepository puntosNoConformidadRepository, ContenidoPuntoNoConformidadRepository contenidoPuntoNoConformidadRepository) {
        this.contenidoPuntoNoConformidadRepository = contenidoPuntoNoConformidadRepository;
        this.puntosNoConformidadRepository = puntosNoConformidadRepository;
        this.noConformidadRepository = noConformidadRepository;
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
        PuntosNoConformidad puntosNoConformidad = puntosNoConformidadRepository.findById(respuestaNoConformidadesProyectoDto.getIdNoConformidad()).orElse(null);
        if (puntosNoConformidad == null) {
            return false;
        }
        if (puntosNoConformidad.getEstado() == Estado.CERRADA){
            return false;
        }
        ContenidoPuntoNoConformidad contenidoPuntoNoConformidad = ContenidoPuntoNoConformidad.builder()
                .contenido(respuestaNoConformidadesProyectoDto.getContenido())
                .fecha(new Date())
                .puntosNoConformidad(puntosNoConformidad)
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
            PuntosNoConformidad puntosNoConformidad = puntosNoConformidadRepository.findById(idPuntoNc).orElse(null);
            if (puntosNoConformidad == null) {
                return false;
            }

            // Obtener los puntos de la no conformidad asociada
            List<PuntosNoConformidad> puntosNC = puntosNoConformidadRepository.findByNoConformidadId(puntosNoConformidad.getNoConformidad().getId());

            // Verificar si el número de puntos es impar (falta una respuesta)
            if (puntosNC.size() % 2 != 0) {
                return false;  // No se puede cerrar si el número de puntos no es par
            }

            // Establecer el estado del punto de no conformidad a CERRADA
            puntosNoConformidad.setEstado(Estado.CERRADA);
            puntosNoConformidadRepository.save(puntosNoConformidad);

            // Verificar si todos los puntos de la no conformidad están cerrados
            boolean todosCerrados = puntosNC.stream().allMatch(punto -> punto.getEstado() == Estado.CERRADA);

            if (todosCerrados) {
                // Si todos los puntos están cerrados, cerrar la no conformidad
                NoConformidad noConformidad = puntosNoConformidad.getNoConformidad();
                noConformidad.setEstado(Estado.CERRADA);
                noConformidadRepository.save(noConformidad);
            }

            return true;
        } catch (Exception e) {
            log.error("Error al cerrar el punto de no conformidad", e);
            return false;
        }
    }

}
