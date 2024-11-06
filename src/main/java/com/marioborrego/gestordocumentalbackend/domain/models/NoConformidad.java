package com.marioborrego.gestordocumentalbackend.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Responsable;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoNc;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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


    @OneToMany(mappedBy = "noConformidad", cascade = CascadeType.ALL)
    private List<ContenidoNoConformidad> contenidos;

    private Date fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Responsable responsable;


    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyectos proyecto;
}

