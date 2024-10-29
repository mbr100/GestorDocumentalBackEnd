package com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListarProyectoEmpleadoDTO {
    String idProyecto;
    String titulo;
    int ano;
    String cliente;
}
