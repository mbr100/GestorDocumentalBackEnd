package com.marioborrego.gestordocumentalbackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpleado;
    private String nombre;
    private String email;
    private String telefono;

    @JoinColumn(name = "rol")
    @ManyToOne
    private Rol rol;

    @ManyToMany(mappedBy = "empleados")
    private Set<Proyectos> proyectos;

}
