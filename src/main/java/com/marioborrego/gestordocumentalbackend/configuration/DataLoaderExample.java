package com.marioborrego.gestordocumentalbackend.configuration;

import com.marioborrego.gestordocumentalbackend.domain.models.Carpeta;
import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.models.Proyecto;
import com.marioborrego.gestordocumentalbackend.domain.models.Rol;
import com.marioborrego.gestordocumentalbackend.domain.repositories.CarpetaRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.ProyectoRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.UsuarioRepository;
import com.marioborrego.gestordocumentalbackend.domain.repositories.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataLoaderExample {
    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepository, UsuarioRepository usuarioRepository, ProyectoRepository proyectoRepository, CarpetaRepository carpetaRepository) {
        return _ -> {
            // Crear roles
            Rol gestorProyectos = new Rol("Gestor de proyectos");
            Rol gestorExpertos = new Rol("Gestor de expertos");
            Rol comercial = new Rol("Comercial");
            Rol administrador = new Rol("Administrador");

            // Guardar roles en la base de datos
            rolRepository.save(gestorProyectos);
            rolRepository.save(gestorExpertos);
            rolRepository.save(comercial);
            rolRepository.save(administrador);

            Usuario usuario1 = Usuario.builder()
                    .nombre("Mario")
                    .email("mario@ejemplo.com")
                    .telefono("123456789")
                    .rol(gestorProyectos)
                    .build();
            Usuario usuario11 = Usuario.builder()
                    .nombre("Alma beniro")
                    .email("ew23rf4gt5@gmail.com")
                    .telefono("123456789")
                    .rol(gestorProyectos)
                    .build();
            Usuario usuario2 = Usuario.builder()
                    .nombre("Alma")
                    .email("alma@ejemplo.com")
                    .telefono("987654321")
                    .rol(gestorExpertos)
                    .build();

            Usuario usuario3 = Usuario.builder()
                    .nombre("Carlos")
                    .email("carlos@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            Usuario usuario4 = Usuario.builder()
                    .nombre("Pepe")
                    .email("pepe@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            Usuario usuario5 = Usuario.builder()
                    .nombre("ejemplo1")
                    .email("ejemplo1@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            Usuario usuario6 = Usuario.builder()
                    .nombre("ejemplo2")
                    .email("ejemplo2@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            // Guardar empleados en la base de datos
            usuarioRepository.save(usuario1);
            usuarioRepository.save(usuario2);
            usuarioRepository.save(usuario3);
            usuarioRepository.save(usuario4);
            usuarioRepository.save(usuario5);
            usuarioRepository.save(usuario6);
            usuarioRepository.save(usuario11);

            // Crear un proyecto de prueba
            Set<Usuario> empleadosProyecto = new HashSet<>();
            empleadosProyecto.add(usuario1);
            empleadosProyecto.add(usuario2);
            empleadosProyecto.add(usuario3);

            Set<Usuario> empleadosProyecto2 = new HashSet<>();

            Proyecto proyecto1 = Proyecto.builder()
                    .codigo(1)
                    .titulo("Proyecto I+D")
                    .ano(2023)
                    .cliente("Cliente 1")
                    .usuarios(empleadosProyecto)  // Asignar los empleados al proyecto
                    .build();

            Proyecto proyecto2 = Proyecto.builder()
                    .codigo(2)
                    .titulo("Proyecto I+D+i")
                    .ano(2023)
                    .cliente("Cliente 2")
                    .usuarios(empleadosProyecto2)  // Asignar los empleados al proyecto
                    .build();

            // Guardar el proyecto en la base de datos
            proyectoRepository.save(proyecto1);
            proyectoRepository.save(proyecto2);

            // Crear carpeta principal para el proyecto
            Carpeta carpetaPrincipal = Carpeta.builder()
                    .nombre("Proyecto I+D")
                    .build();

            // Estructura de carpetas
            Carpeta oferta = Carpeta.builder()
                    .nombre("Oferta")
                    .padre(carpetaPrincipal)
                    .build();

            Carpeta aceptacion = Carpeta.builder()
                    .nombre("Aceptación")
                    .padre(oferta)
                    .build();

            Carpeta ampliacionOferta = Carpeta.builder()
                    .nombre("Ampliación de oferta")
                    .padre(oferta)
                    .build();

            Carpeta cliente = Carpeta.builder()
                    .nombre("Cliente")
                    .padre(carpetaPrincipal)
                    .build();

            Carpeta memoria = Carpeta.builder()
                    .nombre("Memoria")
                    .padre(cliente)
                    .build();

            Carpeta anexo2 = Carpeta.builder()
                    .nombre("Anexo 2")
                    .padre(cliente)
                    .build();

            Carpeta anexoSw = Carpeta.builder()
                    .nombre("Anexo sw")
                    .padre(cliente)
                    .build();

            Carpeta fichasAmpliacion = Carpeta.builder()
                    .nombre("Fichas de Ampliación")
                    .padre(cliente)
                    .build();

            // Crear subcarpetas de fichas de ampliación
            Carpeta ficha21 = Carpeta.builder()
                    .nombre("Fichas 2.1")
                    .padre(fichasAmpliacion)
                    .build();
            Carpeta ficha22 = Carpeta.builder()
                    .nombre("Fichas 2.2")
                    .padre(fichasAmpliacion)
                    .build();
            Carpeta ficha23 = Carpeta.builder()
                    .nombre("Fichas 2.3")
                    .padre(fichasAmpliacion)
                    .build();
            Carpeta ficha24 = Carpeta.builder()
                    .nombre("Fichas 2.4")
                    .padre(fichasAmpliacion)
                    .build();
            Carpeta ficha25 = Carpeta.builder()
                    .nombre("Fichas 2.5")
                    .padre(fichasAmpliacion)
                    .build();

            Carpeta documentosJustificativos = Carpeta.builder()
                    .nombre("Documentos Justificativos")
                    .padre(cliente)
                    .build();

            Carpeta imv = Carpeta.builder()
                    .nombre("IMV")
                    .padre(cliente)
                    .build();

            Carpeta contable = Carpeta.builder()
                    .nombre("Contable")
                    .padre(carpetaPrincipal)
                    .build();

            Carpeta gestorProyecto = Carpeta.builder()
                    .nombre("Gestor del proyecto")
                    .padre(carpetaPrincipal)
                    .build();

            // Agregar las carpetas a la base de datos
            carpetaRepository.save(carpetaPrincipal);
            carpetaRepository.save(oferta);
            carpetaRepository.save(aceptacion);
            carpetaRepository.save(ampliacionOferta);
            carpetaRepository.save(cliente);
            carpetaRepository.save(memoria);
            carpetaRepository.save(anexo2);
            carpetaRepository.save(anexoSw);
            carpetaRepository.save(fichasAmpliacion);
            carpetaRepository.save(ficha21);
            carpetaRepository.save(ficha22);
            carpetaRepository.save(ficha23);
            carpetaRepository.save(ficha24);
            carpetaRepository.save(ficha25);
            carpetaRepository.save(documentosJustificativos);
            carpetaRepository.save(imv);
            carpetaRepository.save(contable);
            carpetaRepository.save(gestorProyecto);
        };
    }
}
