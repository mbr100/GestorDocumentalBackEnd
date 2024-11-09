package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContenidoNoConformidadDto {
    Long id;
    String contenido;
    int orden;
}