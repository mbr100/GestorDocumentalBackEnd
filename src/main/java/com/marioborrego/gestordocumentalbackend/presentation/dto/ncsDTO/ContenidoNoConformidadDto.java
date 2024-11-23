package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import com.marioborrego.gestordocumentalbackend.domain.models.ContenidoPuntoNoConformidad;
import lombok.*;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContenidoNoConformidadDto {
    Long id;
    String contenido;
    Date fecha;


    public ContenidoNoConformidadDto(ContenidoPuntoNoConformidad contenidoPuntoNoConformidad) {
        this.id = contenidoPuntoNoConformidad.getId();
        this.contenido = contenidoPuntoNoConformidad.getContenido();
        this.fecha = contenidoPuntoNoConformidad.getFecha();
    }
}