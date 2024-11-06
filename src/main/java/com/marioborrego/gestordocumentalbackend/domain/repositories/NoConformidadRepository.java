package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoConformidadRepository extends JpaRepository<NoConformidad, Long> {
    @Query("select n from NoConformidad n where n.proyecto.codigo =:idproyecto")
    List<NoConformidad> findNoConformidadByProyecto(@Param("idproyecto") Long idproyecto);
}