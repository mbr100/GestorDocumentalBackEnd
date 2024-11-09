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


            // Crear no conformidades
            ContenidoPuntoNoConformidad contenidoNC1_1 = ContenidoPuntoNoConformidad.builder()
                    .contenido("No se localizan los documentos")
                    .fecha(new Date(1693526400000L))
                    .build();
            ContenidoPuntoNoConformidad contenidoNC1_2 = ContenidoPuntoNoConformidad.builder()
                    .contenido("Se aportan documentos")
                    .fecha(new Date(1693612800000L))
                    .build();
            ContenidoPuntoNoConformidad contenidoNC1_3 = ContenidoPuntoNoConformidad.builder()
                    .contenido("Los documentos aportados no cumplen los requisitos minimos")
                    .fecha(new Date(1693699200000L))
                    .build();
            List<ContenidoPuntoNoConformidad> contenidosNC1 = new ArrayList<>();
            contenidosNC1.add(contenidoNC1_1);
            contenidosNC1.add(contenidoNC1_2);
            contenidosNC1.add(contenidoNC1_3);

            PuntosNoConformidad nc1_1 = PuntosNoConformidad.builder()
                    .contenidos(contenidosNC1)
                    .fecha(new Date())
                    .estado(Estado.ABIERTA)
                    .responsable(Responsable.Cliente)
                    .build();

            List<PuntosNoConformidad> noConformidades1 = new ArrayList<>();
            noConformidades1.add(nc1_1);

            NoConformidad nc1 = NoConformidad.builder()
                    .tipoNc(TipoNc.GP)
                    .proyecto(proyecto1)
                    .version(1)
                    .puntosNoConformidades(noConformidades1)
                    .build();

            nc1_1.setNoConformidad(nc1);
            contenidoNC1_1.setPuntosNoConformidad(nc1_1);
            contenidoNC1_2.setPuntosNoConformidad(nc1_1);
            contenidoNC1_3.setPuntosNoConformidad(nc1_1);
            noConformidadRepository.save(nc1);

            // Crear contenidos para NoConformidad 1 de Proyecto 1
            ContenidoPuntoNoConformidad contenidoNC2_1 = ContenidoPuntoNoConformidad.builder()
                    .contenido("No se localizan los documentos")
                    .fecha(new Date(1693785600000L))
                    .build();
            ContenidoPuntoNoConformidad contenidoNC2_2 = ContenidoPuntoNoConformidad.builder()
                    .contenido("Se aportan documentos")
                    .fecha(new Date(1693872000000L))
                    .build();
            ContenidoPuntoNoConformidad contenidoNC2_3 = ContenidoPuntoNoConformidad.builder()
                    .contenido("Los documentos aportados no cumplen los requisitos mínimos")
                    .fecha(new Date(1693958400000L))
                    .build();
            List<ContenidoPuntoNoConformidad> contenidosNC2 = Arrays.asList(contenidoNC2_1, contenidoNC2_2, contenidoNC2_3);

            PuntosNoConformidad nc2_1 = PuntosNoConformidad.builder()
                    .contenidos(contenidosNC2)
                    .fecha(new Date())
                    .estado(Estado.ABIERTA)
                    .responsable(Responsable.Cliente)
                    .build();

            contenidoNC2_1.setPuntosNoConformidad(nc2_1);
            contenidoNC2_2.setPuntosNoConformidad(nc2_1);
            contenidoNC2_3.setPuntosNoConformidad(nc2_1);

            // Crear TERCERA NoConformidad para Proyecto 1
            ContenidoPuntoNoConformidad contenidoNC3_1 = ContenidoPuntoNoConformidad.builder()
                    .contenido("Los documentos están incompletos")
                    .fecha(new Date(1694044800000L))
                    .build();
            ContenidoPuntoNoConformidad contenidoNC3_2 = ContenidoPuntoNoConformidad.builder()
                    .contenido("Falta el anexo requerido")
                    .fecha(new Date(1694044800000L))
                    .build();
            List<ContenidoPuntoNoConformidad> contenidosNC3 = Arrays.asList(contenidoNC3_1, contenidoNC3_2);

            PuntosNoConformidad nc3_1 = PuntosNoConformidad.builder()
                    .contenidos(contenidosNC3)
                    .fecha(new Date())
                    .estado(Estado.CERRADA)
                    .responsable(Responsable.Comite)
                    .build();

            contenidoNC3_1.setPuntosNoConformidad(nc3_1);
            contenidoNC3_2.setPuntosNoConformidad(nc3_1);

            List<PuntosNoConformidad> noConformidades3 = Arrays.asList(nc2_1, nc3_1);

            NoConformidad noConformidadProyecto1 = NoConformidad.builder()
                    .tipoNc(TipoNc.GP)
                    .proyecto(proyecto1)
                    .version(1)
                    .puntosNoConformidades(noConformidades3)
                    .build();

            nc2_1.setNoConformidad(noConformidadProyecto1);
            nc3_1.setNoConformidad(noConformidadProyecto1);
            noConformidadRepository.save(noConformidadProyecto1);

        // Crear cuatro NoConformidades para Proyecto 2
            for (int i = 1; i <= 4; i++) {
                List<ContenidoPuntoNoConformidad> contenidosProyecto2 = new ArrayList<>();
                for (int j = 1; j <= 2; j++) {
                    ContenidoPuntoNoConformidad contenido = ContenidoPuntoNoConformidad.builder()
                            .contenido("Contenido de No Conformidad " + i + ", elemento " + j)
                            .fecha(new Date(1694044800000L + (1000L*j)))
                            .build();
                    contenidosProyecto2.add(contenido);
                }
                PuntosNoConformidad noConformidadesProyecto2 = PuntosNoConformidad.builder()
                        .contenidos(contenidosProyecto2)
                        .fecha(new Date())
                        .estado(i % 2 == 0 ? Estado.CERRADA : Estado.ABIERTA)
                        .responsable(Responsable.ExpertoTecnico)
                        .build();

                contenidosProyecto2.forEach(contenido -> contenido.setPuntosNoConformidad(noConformidadesProyecto2));

                NoConformidad noConformidadProyecto2 = NoConformidad.builder()
                        .tipoNc(TipoNc.Experto4D)
                        .proyecto(proyecto2)
                        .version(i)
                        .puntosNoConformidades(Collections.singletonList(noConformidadesProyecto2))
                        .build();

                noConformidadesProyecto2.setNoConformidad(noConformidadProyecto2);
                noConformidadRepository.save(noConformidadProyecto2);
            }
        };
    }
}
