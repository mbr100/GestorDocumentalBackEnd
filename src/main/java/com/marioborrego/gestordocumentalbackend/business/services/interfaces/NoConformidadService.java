package com.marioborrego.gestordocumentalbackend.business.services.interfaces;

import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO.RespuestaPuntoNoConformidad;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoConformidadService {
    List<NoConformidad> noConformidadesPorProyecto(String idProyecto);
    boolean responderNoConformidad(RespuestaPuntoNoConformidad respuestaNoConformidadesProyectoDto);
    boolean cerrarPuntoNc(Long idPuntoNc);
}
