package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoConformidadRepository extends JpaRepository<NoConformidad, Long> {
}