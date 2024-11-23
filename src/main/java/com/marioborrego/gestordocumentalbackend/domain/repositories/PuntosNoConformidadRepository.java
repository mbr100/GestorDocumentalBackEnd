package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.PuntosNoConformidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PuntosNoConformidadRepository extends JpaRepository<PuntosNoConformidad, Long> {
    @Query("SELECT p FROM PuntosNoConformidad p WHERE p.noConformidad.id = :noConformidadId")
    List<PuntosNoConformidad> findByNoConformidadId(@Param("noConformidadId") Long noConformidadId);
}