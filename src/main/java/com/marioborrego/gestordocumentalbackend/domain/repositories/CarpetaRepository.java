package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.Carpeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarpetaRepository extends JpaRepository<Carpeta, Long> {

}