package com.marioborrego.gestordocumentalbackend.repositories;

import com.marioborrego.gestordocumentalbackend.models.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProyectosRepository extends JpaRepository<Proyectos, String> {
  }