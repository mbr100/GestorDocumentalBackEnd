package com.marioborrego.gestordocumentalbackend.business.services.interfaces;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CarpetaService {
    void createProjectDirectory(int projectId) throws IOException;
    String uploadFile(String projectId, MultipartFile file) throws IOException;
    List<String> listFiles(String projectId) throws IOException;
    boolean deleteFile(String projectId, String fileName) throws IOException;
}
