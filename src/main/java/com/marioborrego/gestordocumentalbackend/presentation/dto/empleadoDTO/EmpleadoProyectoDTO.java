package com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoProyectoDTO {
    String nombre;
    String email;
    String telefono;
    String rol;
}
