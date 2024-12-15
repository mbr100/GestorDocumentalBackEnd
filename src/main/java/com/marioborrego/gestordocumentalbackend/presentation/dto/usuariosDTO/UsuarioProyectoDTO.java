package com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProyectoDTO {
    String nombre;
    String email;
    String telefono;
    String rol;
}
