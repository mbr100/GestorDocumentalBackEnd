package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProyectosDto {
    int codigo;
    String titulo;
    int ano;
    String cliente;
}