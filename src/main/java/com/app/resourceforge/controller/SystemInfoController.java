package com.app.resourceforge.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.SystemInfo;
import com.app.resourceforge.service.SystemInfoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/system-info")
@RequiredArgsConstructor
public class SystemInfoController {
    private final SystemInfoService service;

    @PostMapping
    public ResponseEntity<?> saveSystemInfo(@RequestBody SystemInfo info) {
        return service.saveSystemInfo(info);

    }

    @GetMapping("download-info-collector")
    public ResponseEntity<Resource> downloadRenamedFile() {
        return service.downloadRenamedFile();
    }
}
