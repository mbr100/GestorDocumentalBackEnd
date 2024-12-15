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
@Entity(name = "Proyecto")
public class Proyecto {
    @Id
    @Column(name = "codigo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "titulo", unique = true, nullable = false)
    private String titulo;

    @Column(name = "acronimo", unique = true, nullable = false)
    private String acronimo;

    @Column(name = "ano")
    private int ano;

    @Column(name = "cliente")
    private String cliente;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "proyecto_usuario",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "proyecto_codigo"),  // Columna de proyecto
            inverseJoinColumns = @JoinColumn(name = "empleado_id")  // Columna de empleado
    )
    private Set<Usuario> usuarios;  // Empleados asociados al proyecto

    @JsonIgnore
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
    private Set<NoConformidad> noConformidad;  // No conformidades asociadas al proyecto
}
