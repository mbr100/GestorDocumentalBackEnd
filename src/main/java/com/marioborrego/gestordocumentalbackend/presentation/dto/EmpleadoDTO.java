package com.marioborrego.gestordocumentalbackend.presentation.dto;

import com.marioborrego.gestordocumentalbackend.models.Rol;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoDTO implements Serializable {
    int idEmpleado;
    String nombre;
    String email;
    String telefono;
    String rol;
}