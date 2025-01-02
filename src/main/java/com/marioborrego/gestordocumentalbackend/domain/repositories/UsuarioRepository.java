package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM  Usuario u JOIN u.proyectos p WHERE u.idUsuario = :idUsuario")
    boolean existsProyectosByEmpleadoId(int idUsuario);

    Usuario findByNombre(String nombre);

    @Query("SELECT p FROM Usuario u JOIN u.proyectos p WHERE u.idUsuario = :idUsuario")
    List<Proyecto> getProyectosEmpleado(int idUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    List<Usuario> findUsuarioByActivo();

    Optional<Usuario> findUsuariosByNombre(String nombre);
}