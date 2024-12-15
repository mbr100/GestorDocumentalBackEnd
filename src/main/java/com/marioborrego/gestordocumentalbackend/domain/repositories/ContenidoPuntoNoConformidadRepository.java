package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.ContenidoPuntoNoConformidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContenidoPuntoNoConformidadRepository extends JpaRepository<ContenidoPuntoNoConformidad, Long> {
}