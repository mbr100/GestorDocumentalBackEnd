package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoConformidadRepository extends JpaRepository<NoConformidad, Long> {

  @Query("SELECT nc FROM NoConformidad nc WHERE nc.proyecto.codigo =:idProyecto")
  List<NoConformidad> noConformidadesPorProyecto(Long idProyecto);
}