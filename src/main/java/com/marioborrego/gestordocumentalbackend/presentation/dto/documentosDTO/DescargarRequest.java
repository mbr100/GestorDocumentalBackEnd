package com.marioborrego.gestordocumentalbackend.presentation.dto.documentosDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class DescargarRequest implements Serializable {
    private String nombreArchivo;
    private String ruta;
}
