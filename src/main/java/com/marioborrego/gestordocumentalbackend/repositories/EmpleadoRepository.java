package com.marioborrego.gestordocumentalbackend.repositories;

import com.marioborrego.gestordocumentalbackend.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM empleado e JOIN e.proyectos p WHERE e.idEmpleado = :idEmpleado")
    boolean existsProyectosByEmpleadoId(int idEmpleado);

    @Query("SELECT e FROM empleado e WHERE e.nombre = :nombre")
    Empleado findByNombre(String nombre);
}