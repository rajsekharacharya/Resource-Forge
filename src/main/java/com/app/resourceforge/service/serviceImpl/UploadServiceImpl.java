package com.app.resourceforge.service.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.resourceforge.model.UploadFileModel;
import com.app.resourceforge.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;

    @Value("${file.upload-dir.windows}")
    private String windowsUploadDir;

    @Value("${file.upload-dir.linux}")
    private String linuxUploadDir;

    @Override
    public ResponseEntity<?> uploadFile(UploadFileModel form) throws IOException {

        UUID uuid = UUID.randomUUID();
        String os = System.getProperty("os.name").toLowerCase();
        String baseDir = os.contains("win") ? windowsUploadDir : linuxUploadDir;
        File uploadDir = new File(baseDir + "/" + form.getModule());
        // Create the directory if it does not exist
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create directory");
        }

        MultipartFile file = form.getFile();
        String random = uuid.toString();
        String name = file.getOriginalFilename();

        String[] part = name.split("\\.");
        String extension = part[part.length - 1];
        String filename = random + "." + extension;

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file not found");
        } else {
            String uploadFilePath = baseDir + "/" + form.getModule() + "/" + filename;
            String accessFilePath = "uploads/" + form.getModule() + "/" + filename;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            return ResponseEntity.status(HttpStatus.OK).body(accessFilePath);
        }

    }

    @Override
    public ResponseEntity<?> getFile(String link) throws MalformedURLException {
        String os = System.getProperty("os.name").toLowerCase();
        String baseDir = os.contains("win") ? windowsUploadDir : linuxUploadDir;
        String absolutePath = link.replace("uploads", baseDir);
        File file = new File(absolutePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        } else {
            Resource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        }
    }

}
