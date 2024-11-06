package com.marioborrego.gestordocumentalbackend.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contenido_nc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContenidoNoConformidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido; // Podría ser la pregunta o la respuesta

    private int orden; // Orden de la pregunta/respuesta

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "no_conformidad_id")
    private NoConformidad noConformidad; // Relación con la no conformidad padre
}
