package com.marioborrego.gestordocumentalbackend.models;

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
    private String codigo;

    @Column(name = "nombre")
    private String titulo;

    @Column(name = "ano")
    private int ano;

    @Column(name = "cliente")
    private String cliente;

    @ManyToMany
    @JoinTable(
            name = "proyecto_empleado",  // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "proyecto_codigo"),  // Columna de proyecto
            inverseJoinColumns = @JoinColumn(name = "empleado_id")  // Columna de empleado
    )
    private Set<Empleado> empleados;  // Empleados asociados al proyecto
}
