package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Proyecto p WHERE p.titulo = :titulo")
    boolean findbytitulo(String titulo);

    @Query("SELECT p FROM Proyecto p WHERE p.codigo = :codigo")
    Proyecto findByCodigo(int codigo);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Proyecto p WHERE p.codigo = :i")
    boolean existsByCodigo(int i);
}