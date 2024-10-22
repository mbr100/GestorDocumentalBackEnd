package com.marioborrego.gestordocumentalbackend.configuration;

import com.marioborrego.gestordocumentalbackend.models.Empleado;
import com.marioborrego.gestordocumentalbackend.models.Proyectos;
import com.marioborrego.gestordocumentalbackend.models.Rol;
import com.marioborrego.gestordocumentalbackend.repositories.EmpleadoRepository;
import com.marioborrego.gestordocumentalbackend.repositories.ProyectosRepository;
import com.marioborrego.gestordocumentalbackend.repositories.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoaderExample {
    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepository, EmpleadoRepository empleadoRepository, ProyectosRepository proyectosRepository) {
        return args -> {
            // Crear roles
            Rol gestorProyectos = new Rol("Gestor de proyectos");
            Rol gestorExpertos = new Rol("Gestor de expertos");
            Rol comercial = new Rol("Comercial");
            Rol administrador = new Rol("Administrador");
            Rol rol = new Rol("Rol");
            Rol rol1 = new Rol("Rol1");
            Rol rol2 = new Rol("Rol2");
            Rol rol3 = new Rol("Rol3");
            // Guardar roles en la base de datos
            rolRepository.save(gestorProyectos);
            rolRepository.save(gestorExpertos);
            rolRepository.save(comercial);
            rolRepository.save(administrador);
            rolRepository.save(rol);
            rolRepository.save(rol1);
            rolRepository.save(rol2);
            rolRepository.save(rol3);

            Empleado empleado1 = Empleado.builder()
                    .nombre("Mario")
                    .email("mario@ejemplo.com")
                    .telefono("123456789")
                    .rol(gestorProyectos)
                    .build();
            Empleado empleado2 = Empleado.builder()
                    .nombre("Alma")
                    .email("alma@ejemplo.com")
                    .telefono("987654321")
                    .rol(gestorExpertos)
                    .build();

            Empleado empleado3 = Empleado.builder()
                    .nombre("Carlos")
                    .email("carlos@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            // Guardar empleados en la base de datos
            empleadoRepository.save(empleado1);
            empleadoRepository.save(empleado2);
            empleadoRepository.save(empleado3);

            // Crear un proyecto de prueba
            Set<Empleado> empleadosProyecto = new HashSet<>();
            empleadosProyecto.add(empleado1);
            empleadosProyecto.add(empleado2);
            empleadosProyecto.add(empleado3);

            Proyectos proyecto1 = Proyectos.builder()
                    .codigo("001.002.003")
                    .titulo("Proyecto I+D")
                    .ano(2023)
                    .cliente("Cliente 1")
                    .empleados(empleadosProyecto)  // Asignar los empleados al proyecto
                    .build();

            // Guardar el proyecto en la base de datos
            proyectosRepository.save(proyecto1);

        };

    }
}
