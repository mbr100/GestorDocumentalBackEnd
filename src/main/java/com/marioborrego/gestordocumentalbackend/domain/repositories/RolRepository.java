package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {
    @Query("SELECT r FROM rol r")
    List<Rol> findRol();

    @Query("SELECT r FROM rol r WHERE r.Rol = :rol")
    Rol findByRol(String rol);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.rol.Rol = :rol")
    long contarEmpleadosPorRol(@Param("rol") String rol);

    @Modifying
    @Query("UPDATE rol r SET r.Rol = :nuevoRol WHERE r.Rol = :antiguoRol")
    void actualizarRol(@Param("nuevoRol") String nuevoRol, @Param("antiguoRol") String antiuguoRol);
}