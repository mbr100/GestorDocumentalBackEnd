package com.marioborrego.gestordocumentalbackend.domain.models;

import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Responsable;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "puntos_no_conformidad")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PuntosNoConformidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "puntosNoConformidad", cascade = CascadeType.ALL)
    private List<ContenidoPuntoNoConformidad> contenidos;

    private Date fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Responsable responsable;

    @ManyToOne
    @JoinColumn(name = "no_conformidad_id")
    private NoConformidad noConformidad; // Relación con la no conformidad padre

}

