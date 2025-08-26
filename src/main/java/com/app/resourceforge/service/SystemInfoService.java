package com.app.resourceforge.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.SystemInfo;

public interface SystemInfoService {

    ResponseEntity<?> saveSystemInfo(SystemInfo systemInfo);

    ResponseEntity<Resource> downloadRenamedFile();

}
