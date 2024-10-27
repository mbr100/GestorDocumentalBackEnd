package com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO;

import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.EmpleadoProyectoDTO;
import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
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
    List<EmpleadoProyectoDTO> empleadoProyecto;
}
