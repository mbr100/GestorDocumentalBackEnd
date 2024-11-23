package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespuestaPuntoNoConformidad {
    private String contenido;
    private Date fecha;
    private Long idNoConformidad;
}
