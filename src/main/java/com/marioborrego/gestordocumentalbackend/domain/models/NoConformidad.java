package com.marioborrego.gestordocumentalbackend.domain.models;

import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoNc;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "no_conformidad")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NoConformidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoNc tipoNc;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyectos proyecto;

    @OneToMany(mappedBy = "noConformidad", cascade = CascadeType.ALL)
    private List<PuntosNoConformidad> puntosNoConformidades;

    int version;
}
