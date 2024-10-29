package com.marioborrego.gestordocumentalbackend.business.services.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public interface CarpetaService {
    void createProjectDirectory(int projectId) throws IOException;
    Map<String,Object> archivosProyectoParaEmpleados(String projectId) throws IOException;
    boolean guardarDocumentoProyecto(String projectId, String carpeta, MultipartFile documento) throws IOException;
    boolean aceptarDocumento(String idProyecto, String ruta, String documento) throws IOException;
    boolean rechazarDocumento(String idProyecto, String ruta, String documento) throws IOException;
}
