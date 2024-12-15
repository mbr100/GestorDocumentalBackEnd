package com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class CrearUsuarioDTO {
    String nombre;
    String email;
    String telefono;
    String password;
    String rol;
}
