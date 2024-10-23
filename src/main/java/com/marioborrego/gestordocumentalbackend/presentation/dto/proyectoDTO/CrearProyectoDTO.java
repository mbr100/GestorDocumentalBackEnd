package com.marioborrego.gestordocumentalbackend.presentation.dto.proyectoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearProyectoDTO {
    private String codigo;
    private String titulo;
    private int ano;
    private String cliente;
    private String idEmpleado;
}
