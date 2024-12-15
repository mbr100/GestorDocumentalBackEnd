package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import com.marioborrego.gestordocumentalbackend.domain.models.Proyecto;
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

    public ProyectosDto(Proyecto proyecto) {
        this.codigo = proyecto.getCodigo();
        this.titulo = proyecto.getTitulo();
        this.ano = proyecto.getAno();
        this.cliente = proyecto.getCliente();
    }
}