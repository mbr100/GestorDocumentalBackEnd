package com.marioborrego.gestordocumentalbackend.configuration;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.CarpetaService;
import com.marioborrego.gestordocumentalbackend.domain.models.*;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Estado;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.Responsable;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoNc;
import com.marioborrego.gestordocumentalbackend.domain.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataLoaderExample {
    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepository, EmpleadoRepository empleadoRepository, ProyectosRepository proyectosRepository, CarpetaRepository carpetaRepository,
                                   NoConformidadRepository noConformidadRepository, CarpetaService carpetaService) {
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

            Empleado empleado1 = Empleado.builder()
                    .nombre("Mario")
                    .email("mario@ejemplo.com")
                    .telefono("123456789")
                    .rol(gestorProyectos)
                    .build();
            Empleado empleado11 = Empleado.builder()
                    .nombre("Alma beniro")
                    .email("ew23rf4gt5@gmail.com")
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

            Empleado empleado4 = Empleado.builder()
                    .nombre("Pepe")
                    .email("pepe@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            Empleado empleado5 = Empleado.builder()
                    .nombre("ejemplo1")
                    .email("ejemplo1@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            Empleado empleado6 = Empleado.builder()
                    .nombre("ejemplo2")
                    .email("ejemplo2@ejemplo.com")
                    .telefono("555666777")
                    .rol(comercial)
                    .build();

            Empleado empleado7 = Empleado.builder()
                    .nombre("admin")
                    .email("admin@ejemplo.com")
                    .telefono("555666777")
                    .rol(administrador)
                    .build();

            // Guardar empleados en la base de datos
            empleadoRepository.save(empleado1);
            empleadoRepository.save(empleado2);
            empleadoRepository.save(empleado3);
            empleadoRepository.save(empleado4);
            empleadoRepository.save(empleado5);
            empleadoRepository.save(empleado6);
            empleadoRepository.save(empleado11);
            empleadoRepository.save(empleado7);

            // Crear un proyecto de prueba
            Set<Empleado> empleadosProyecto = new HashSet<>();
            empleadosProyecto.add(empleado1);
            empleadosProyecto.add(empleado2);
            empleadosProyecto.add(empleado3);

            Set<Empleado> empleadosProyecto2 = new HashSet<>();

            Proyectos proyecto1 = Proyectos.builder()
                    .codigo(1)
                    .titulo("Proyecto I+D")
                    .ano(2023)
                    .cliente("Cliente 1")
                    .empleados(empleadosProyecto)  // Asignar los empleados al proyecto
                    .build();

            Proyectos proyecto2 = Proyectos.builder()
                    .codigo(2)
                    .titulo("Proyecto I+D+i")
                    .ano(2023)
                    .cliente("Cliente 2")
                    .empleados(empleadosProyecto2)  // Asignar los empleados al proyecto
                    .build();



            // Guardar el proyecto en la base de datos
            proyectosRepository.save(proyecto1);
            proyectosRepository.save(proyecto2);

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

            carpetaService.createProjectDirectory(proyecto1.getCodigo());
            carpetaService.createProjectDirectory(proyecto2.getCodigo());

            NoConformidad nc1 = NoConformidad.builder()
                    .tipoNc(TipoNc.GP)
                    .fecha(new Date())
                    .proyecto(proyecto1)
                    .estado(Estado.CERRADA)
                    .responsable(Responsable.GestorProyecto)
                    .build();
            ContenidoNoConformidad c1 = ContenidoNoConformidad.builder()
                    .contenido("No se ven objetivos")
                    .noConformidad(nc1)
                    .orden(1)
                    .build();
            ContenidoNoConformidad c2 = ContenidoNoConformidad.builder()
                    .contenido("Estan en apartado xxxx")
                    .noConformidad(nc1)
                    .orden(2)
                    .build();
            List<ContenidoNoConformidad> ncgpcontenido = new ArrayList<>();
            ncgpcontenido.add(c1);
            ncgpcontenido.add(c2);
            nc1.setContenidos(ncgpcontenido);
            noConformidadRepository.save(nc1);

            // Contenidos para NoConformidad 2 (Proyecto 1)
            NoConformidad nc2 = NoConformidad.builder()
                    .tipoNc(TipoNc.GP)
                    .fecha(new Date())
                    .proyecto(proyecto1)
                    .estado(Estado.ABIERTA)
                    .responsable(Responsable.GestorProyecto)
                    .build();
            ContenidoNoConformidad c3 = ContenidoNoConformidad.builder()
                    .contenido("Pendiente de datos de rendimiento")
                    .orden(1)
                    .build();
            ContenidoNoConformidad c4 = ContenidoNoConformidad.builder()
                    .contenido("Datos disponibles en sistema")
                    .orden(2)
                    .build();
            List<ContenidoNoConformidad> ncgpcontenido2 = new ArrayList<>();
            ncgpcontenido2.add(c3);
            ncgpcontenido2.add(c4);
            nc2.setContenidos(ncgpcontenido2);
            noConformidadRepository.save(nc2);

            // Contenidos para NoConformidad 3 (Proyecto 2)
            NoConformidad nc3 = NoConformidad.builder()
                    .tipoNc(TipoNc.Contable)
                    .fecha(new Date())
                    .proyecto(proyecto2)
                    .estado(Estado.ABIERTA)
                    .responsable(Responsable.Contable)
                    .build();
            ContenidoNoConformidad c5 = ContenidoNoConformidad.builder()
                    .contenido("Se requiere ajuste en presupuesto")
                    .orden(1)
                    .build();
            ContenidoNoConformidad c6 = ContenidoNoConformidad.builder()
                    .contenido("Se ha realizado ajuste solicitado")
                    .orden(2)
                    .build();
            List<ContenidoNoConformidad> ncgpcontenido3 = new ArrayList<>();
            ncgpcontenido3.add(c5);
            ncgpcontenido3.add(c6);
            nc3.setContenidos(ncgpcontenido3);

            noConformidadRepository.save(nc3);
        };
    }
}
