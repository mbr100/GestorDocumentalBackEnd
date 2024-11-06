package com.marioborrego.gestordocumentalbackend.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "proyectos")
public class Proyectos {
    @Id
    @Column(name = "codigo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "nombre")
    private String titulo;

    @Column(name = "ano")
    private int ano;

    @Column(name = "cliente")
    private String cliente;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "proyecto_empleado",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "proyecto_codigo"),  // Columna de proyecto
            inverseJoinColumns = @JoinColumn(name = "empleado_id")  // Columna de empleado
    )
    private Set<Empleado> empleados;  // Empleados asociados al proyecto

    @JsonIgnore
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private Set<NoConformidad> noConformidades;  // No conformidades asociadas al proyecto
}
