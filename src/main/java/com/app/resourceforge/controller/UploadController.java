package com.app.resourceforge.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.UploadFileModel;
import com.app.resourceforge.service.UploadService;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    UploadService service;

    @PostMapping("/addFile")
    public ResponseEntity<?> uploadFile(@ModelAttribute UploadFileModel form) throws Exception {
        return service.uploadFile(form);
    }

    @GetMapping("/getFile")
    public ResponseEntity<?> getFile(@RequestParam String link) throws MalformedURLException {
        return service.getFile(link);
    }


        @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws MalformedURLException {

                File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        } else {
            Resource resource = new UrlResource(file.toURI());

            try {
                String  contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
            } catch (IOException ex) {
                String contentType = "application/octet-stream";
                return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
            }
        }
    }

}
