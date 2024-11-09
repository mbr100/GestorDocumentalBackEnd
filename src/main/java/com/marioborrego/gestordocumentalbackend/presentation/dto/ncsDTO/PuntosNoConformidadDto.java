package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Responsable;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PuntosNoConformidadDto {
    Long id;
    List<ContenidoNoConformidadDto> contenidos;
    Date fecha;
    Estado estado;
    Responsable responsable;
}