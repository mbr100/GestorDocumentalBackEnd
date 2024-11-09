package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
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
    ProyectosDto proyecto;
    List<PuntosNoConformidadDto> puntosNoConformidades;
    int version;

    public NoConformidadesProyectoDto(NoConformidad noConformidad) {
        this.id = noConformidad.getId();
        this.tipoNc = noConformidad.getTipoNc();

    }
}