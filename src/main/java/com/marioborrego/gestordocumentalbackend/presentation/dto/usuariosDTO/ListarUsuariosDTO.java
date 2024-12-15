package com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListarUsuariosDTO implements Serializable {
    int idUsuario;
    String nombre;
    String email;
    String telefono;
    String rol;
}