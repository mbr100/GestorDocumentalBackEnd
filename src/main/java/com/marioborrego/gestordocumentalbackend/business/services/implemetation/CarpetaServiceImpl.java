package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.CarpetaService;
import com.marioborrego.gestordocumentalbackend.business.utils.CodeProyect;
import com.marioborrego.gestordocumentalbackend.domain.models.Carpeta;
import com.marioborrego.gestordocumentalbackend.domain.repositories.CarpetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CarpetaServiceImpl implements CarpetaService {
    private static final String BASE_DIRECTORY = "/Volumes/MiVolumen/Clientes";
    private static final Logger logger = LoggerFactory.getLogger(CarpetaServiceImpl.class);

    private final CarpetaRepository carpetaRepository;

    public CarpetaServiceImpl(CarpetaRepository carpetaRepository) {
        this.carpetaRepository = carpetaRepository;
    }

    @Transactional
    public void createProjectDirectory(int projectId) throws IOException {
        try {
            String codeProyect = CodeProyect.idToCodeProyect(projectId);
            Path projectPath = Paths.get(BASE_DIRECTORY, codeProyect);
            if (!Files.exists(projectPath)) {
                Files.createDirectories(projectPath);
                List<Carpeta> folderStructure = carpetaRepository.findAll();
                Carpeta rootFolder = folderStructure.stream()
                        .filter(c -> "Proyecto I+D".equals(c.getNombre()))
                        .findFirst()
                        .orElse(null);
                createFoldersRecursively(new File(BASE_DIRECTORY + "/" + codeProyect), rootFolder, folderStructure);
            }
        } catch (IOException e) {
            logger.error("Error al crear el directorio del proyecto", e);
            throw e;
        }
    }

    private void createFoldersRecursively(File baseFolder, Carpeta parentFolder, List<Carpeta> structure) {
        for (Carpeta folder : structure) {
            if ((parentFolder == null && folder.getPadre() == null) || (parentFolder != null && parentFolder.getId().equals(folder.getPadre() != null ? folder.getPadre().getId() : null))) {
                // Crear la carpeta subFolder
                File subFolder = new File(baseFolder, folder.getNombre());
                if (!subFolder.exists()) {
                    subFolder.mkdirs(); // Crea la carpeta solo si no existe
                }
                createFoldersRecursively(subFolder, folder, structure);
            }
        }
    }

    @Override
    public Map<String, Object> archivosProyectoParaEmpleados(String projectId) throws IOException {
        try {
            String rutaProyecto = BASE_DIRECTORY + "/" + projectId;
            File carpetaProyecto = new File(rutaProyecto);
            return obtenerEstructuraCarpetaEnJson(carpetaProyecto);
        } catch (Exception e) {
            throw new IOException("Error al obtener la estructura de la carpeta del proyecto", e);
        }
    }

    @Override
    public boolean guardarDocumentoProyecto(String projectId, String carpeta, MultipartFile documento) throws IOException {
        try {
            String rutaCarpeta = obtenerRutaCarpetaSubida(projectId, carpeta);
            if (rutaCarpeta == null) {
                throw new IOException("No se ha encontrado la carpeta de subida");
            }
            int numeroArchivos = contarArchivos(new File(rutaCarpeta));
            if (numeroArchivos == 0) numeroArchivos = 1;
            Path rutaDocumento = Paths.get(rutaCarpeta, (numeroArchivos +" "+ documento.getOriginalFilename()));
            Files.write(rutaDocumento, documento.getBytes());
            return true;
        } catch (IOException e) {
            logger.error("Error al guardar el documento en la carpeta del proyecto", e);
            throw e;
        }
    }

    @Override
    public boolean aceptarDocumento(String idProyecto, String ruta, String documento) throws IOException {
        String rutaDocumento = obtenerRutaCarpetaSubida(idProyecto, ruta);
        if (rutaDocumento == null) {
            throw new IOException("No se ha encontrado la carpeta de subida");
        }

        File archivo = new File(rutaDocumento, documento);

        // Verificar si el archivo ya está en la carpeta "Aceptado"
        if (archivo.getParentFile().getName().equalsIgnoreCase("Aceptado")) {
            return false;
        }

        if (archivo.exists()) {
            File carpetaPadre = archivo.getParentFile().getName().equalsIgnoreCase("Rechazado")
                    ? archivo.getParentFile().getParentFile()
                    : archivo.getParentFile();

            File carpetaAceptado = new File(carpetaPadre, "Aceptado");

            // Crear la carpeta "Aceptado" si no existe
            if (!carpetaAceptado.exists()) {
                carpetaAceptado.mkdirs();
            }

            File archivoAceptado = new File(carpetaAceptado, documento);
            Files.move(archivo.toPath(), archivoAceptado.toPath());

            // Verificar y eliminar carpeta "Rechazado" si está vacía
            File carpetaRechazado = new File(carpetaPadre, "Rechazado");
            eliminarCarpetaSiVacia(carpetaRechazado);

            return true;
        }
        return false;
    }

    private void eliminarCarpetaSiVacia(File carpeta) {
        if (carpeta.isDirectory() && Objects.requireNonNull(carpeta.list()).length == 0) {
            carpeta.delete();
        }
    }

    @Override
    public boolean rechazarDocumento(String idProyecto, String ruta, String documento) throws IOException {
        String rutaDocumento = obtenerRutaCarpetaSubida(idProyecto, ruta);
        if (rutaDocumento == null) {
            throw new IOException("No se ha encontrado la carpeta de subida");
        }

        File archivo = new File(rutaDocumento, documento);

        // Verificar si el archivo ya está en la carpeta "Rechazado"
        if (archivo.getParentFile().getName().equalsIgnoreCase("Rechazado")) {
            return false;
        }

        if (archivo.exists()) {
            File carpetaPadre = archivo.getParentFile().getName().equalsIgnoreCase("Aceptado")
                    ? archivo.getParentFile().getParentFile()
                    : archivo.getParentFile();

            File carpetaRechazado = new File(carpetaPadre, "Rechazado");

            // Crear la carpeta "Rechazado" si no existe
            if (!carpetaRechazado.exists()) {
                carpetaRechazado.mkdirs();
            }

            File archivoRechazado = new File(carpetaRechazado, documento);
            Files.move(archivo.toPath(), archivoRechazado.toPath());

            // Verificar y eliminar carpeta "Aceptado" si está vacía
            File carpetaAceptado = new File(carpetaPadre, "Aceptado");
            eliminarCarpetaSiVacia(carpetaAceptado);

            return true;
        }
        return false;
    }




    private Map<String, Object> obtenerEstructuraCarpetaEnJson(File carpeta) {
        Map<String, Object> mapaCarpeta = new HashMap<>();
        mapaCarpeta.put("nombre", carpeta.getName());
        if (carpeta.isDirectory()) {
            List<Object> hijos = new ArrayList<>();
            for (File fichero : Objects.requireNonNull(carpeta.listFiles())) {
                if (fichero.getName().equals(".DS_Store")) {
                    continue;
                }
                hijos.add(obtenerEstructuraCarpetaEnJson(fichero));
            }
            mapaCarpeta.put("hijos", hijos);
        } else {
            mapaCarpeta.put("tipo", "fichero");
        }
        return mapaCarpeta;
    }

    private String obtenerRutaCarpetaSubida(String idProyecto, String nombreCarpeta) throws IOException {
        File baseDir = new File(BASE_DIRECTORY+"/"+idProyecto);
        return buscarCarpetaRecursivamente(baseDir, nombreCarpeta);
    }

    private String buscarCarpetaRecursivamente(File directorio, String nombreCarpeta) {
        // Verificamos si el directorio es una carpeta
        if (directorio.isDirectory()) {
            // Listamos los archivos y carpetas dentro de este directorio
            for (File archivo : Objects.requireNonNull(directorio.listFiles())) {
                // Si encontramos la carpeta, retornamos su ruta
                if (archivo.isDirectory() && archivo.getName().equals(nombreCarpeta)) {
                    return archivo.getAbsolutePath();
                }
                // Si no es la carpeta, buscamos en su interior
                String resultado = buscarCarpetaRecursivamente(archivo, nombreCarpeta);
                if (resultado != null) {
                    return resultado; // Retornamos la ruta si la encontramos
                }
            }
        }
        return null; // Retornamos null si no encontramos la carpeta
    }

    private int contarArchivos(File directorio) {
        int contador = 0;

        // Listamos los archivos y carpetas dentro del directorio
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    contador++; // Incrementa el contador si es un archivo
                } else if (archivo.isDirectory()) {
                    // Llama a sí mismo si es un directorio para contar los archivos dentro
                    contador += contarArchivos(archivo);
                }
            }
        }
        return contador;
    }
}
