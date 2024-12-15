package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoNc;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class NoConformidadesProyectoDto {
    Long id;
    TipoNc tipoNc;
    Estado estado;
    ProyectosDto proyecto;
    List<PuntosNoConformidadDto> puntosNoConformidades;
    int version;

    public NoConformidadesProyectoDto(NoConformidad noConformidad) {
        this.id = noConformidad.getId();
        this.tipoNc = noConformidad.getTipoNc();
        this.proyecto = new ProyectosDto(noConformidad.getProyecto());
        this.puntosNoConformidades = noConformidad.getPuntosNoConformidades().stream().map(PuntosNoConformidadDto::new).toList();
        this.version = noConformidad.getVersion();
        this.estado = noConformidad.getEstado();
    }
}