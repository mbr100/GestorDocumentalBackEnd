package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectosRepository extends JpaRepository<Proyectos, String> {
  @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM proyectos p WHERE p.titulo = :titulo")
  boolean findbytitulo(String titulo);

    @Query("SELECT p FROM proyectos p WHERE p.codigo = :codigo")
    Proyectos findByCodigo(int codigo);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM proyectos p WHERE p.codigo = :i")
    boolean existsByCodigo(int i);
}