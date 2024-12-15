package com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EditarUsuarioDTO {
    String nombre;
    String email;
    String telefono;
    String rol;
}
