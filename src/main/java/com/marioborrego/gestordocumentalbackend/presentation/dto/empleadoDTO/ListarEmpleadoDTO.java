package com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListarEmpleadoDTO implements Serializable {
    int idEmpleado;
    String nombre;
    String email;
    String telefono;
    String rol;
}