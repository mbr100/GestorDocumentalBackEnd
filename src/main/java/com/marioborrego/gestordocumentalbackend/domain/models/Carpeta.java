package com.marioborrego.gestordocumentalbackend.domain.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "carpeta_estructura")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Carpeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "padre_id")
    private Carpeta padre;

    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL)
    private List<Carpeta> subCarpetas;
}
