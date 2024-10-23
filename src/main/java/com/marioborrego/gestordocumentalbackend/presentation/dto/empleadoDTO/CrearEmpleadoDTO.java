package com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class CrearEmpleadoDTO {
    String nombre;
    String email;
    String telefono;
    String rol;
}
