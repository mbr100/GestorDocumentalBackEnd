package com.marioborrego.gestordocumentalbackend.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity(name = "rol")
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String Rol;

    @Enumerated(EnumType.STRING)
    private TipoRol tipoRol;

    @JsonIgnore
    @OneToMany(mappedBy = "rol") // Relación uno a muchos con Empleado
    private Set<Usuario> usuarios; // Lista de empleados que tienen este rol

    public Rol(String rol) {
        Rol = rol;
    }

    public Rol(String rol, TipoRol tipoRol) {
        Rol = rol;
        this.tipoRol = tipoRol;
    }
}
