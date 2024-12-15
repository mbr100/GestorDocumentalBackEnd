package com.marioborrego.gestordocumentalbackend.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "punto_contenido_nc")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContenidoPuntoNoConformidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenido; // Podría ser la pregunta o la respuesta

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "puntos_no_conformidades_id")
    private PuntosNoConformidad puntosNoConformidad; // Relación con la no conformidad padre
}
