package com.marioborrego.gestordocumentalbackend.domain.models;

import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(unique = true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean activo;

    @JoinColumn(name = "rol_id")  // Agregar nombre explícito para la columna de la llave foránea
    @ManyToOne
    private Rol rol;

    @ManyToMany(mappedBy = "usuarios")  // Cambiar "usuariosAsociados" a "usuarios"
    private Set<Proyecto> proyectos;


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(new SimpleGrantedAuthority(rol.getRol()));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        TipoRol rol = this.getRol().getTipoRol();
        return Collections.singletonList(new SimpleGrantedAuthority(rol.toString()));
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo;
    }
}
