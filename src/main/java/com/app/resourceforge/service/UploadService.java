package com.app.resourceforge.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.UploadFileModel;

public interface UploadService {

    ResponseEntity<?> uploadFile(UploadFileModel form) throws IOException;

    ResponseEntity<?> getFile(String link)throws MalformedURLException;
    
}
