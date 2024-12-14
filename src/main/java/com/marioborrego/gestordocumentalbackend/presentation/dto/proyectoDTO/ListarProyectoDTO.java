package com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO;

import com.marioborrego.gestordocumentalbackend.presentation.dto.usuariosDTO.UsuarioProyectoDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListarProyectoDTO {
    String idProyecto;
    String titulo;
    int ano;
    String cliente;
    List<UsuarioProyectoDTO> empleadoProyecto;
}
