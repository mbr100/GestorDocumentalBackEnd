package com.marioborrego.gestordocumentalbackend.repositories;

import com.marioborrego.gestordocumentalbackend.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}