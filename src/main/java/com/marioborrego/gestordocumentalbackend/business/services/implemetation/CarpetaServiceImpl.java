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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                // Llamada recursiva para crear subcarpetas
                createFoldersRecursively(subFolder, folder, structure);
            }
        }
    }

    @Transactional
    public String uploadFile(String projectId, MultipartFile file) throws IOException {
        Path projectPath = Paths.get(BASE_DIRECTORY, projectId);
        if (!Files.exists(projectPath)) {
            createProjectDirectory(Integer.parseInt(projectId));
        }

        Path filePath = projectPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }

    public List<String> listFiles(String projectId) throws IOException {
        try {
            List<String> fileNames = new ArrayList<>();
            Path projectPath = Paths.get(BASE_DIRECTORY, projectId);

            if (Files.exists(projectPath)) {
                Files.list(projectPath).forEach(path -> fileNames.add(path.getFileName().toString()));
            }
            return fileNames;
        } catch (IOException e) {
            logger.error("Error al listar los archivos del proyecto", e);
            throw e;
        }
    }

    public boolean deleteFile(String projectId, String fileName) throws IOException {
        Path filePath = Paths.get(BASE_DIRECTORY, projectId, fileName);
        if (Files.exists(filePath)) {
            return Files.deleteIfExists(filePath);
        }
        return false;
    }
}
