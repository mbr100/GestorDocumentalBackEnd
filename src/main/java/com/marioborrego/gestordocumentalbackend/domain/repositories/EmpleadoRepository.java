package com.marioborrego.gestordocumentalbackend.domain.repositories;

import com.marioborrego.gestordocumentalbackend.domain.models.Empleado;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM empleado e JOIN e.proyectos p WHERE e.idEmpleado = :idEmpleado")
    boolean existsProyectosByEmpleadoId(int idEmpleado);

    Empleado findByNombre(String nombre);

    @Query("SELECT p FROM empleado e JOIN e.proyectos p WHERE e.idEmpleado = :idEmpleado")
    List<Proyectos> getProyectosEmpleado(int idEmpleado);
}