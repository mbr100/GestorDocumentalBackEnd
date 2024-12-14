package com.marioborrego.gestordocumentalbackend.domain.models;

import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Responsable;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoNc;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    private String Contenido;

    private Date fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    private Responsable responsable;

    @OneToOne(mappedBy = "noConformidadPadre")  // Corregido el nombre del mappedBy
    private NoConformidad noConformidadHija;

    @ManyToOne
    @JoinColumn(name = "padre_id")
    private NoConformidad noConformidadPadre;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;  // Proyecto al que pertenece la no conformidad
}

