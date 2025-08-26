package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.ProjectMaster;

public interface ProjectMasterService {

    ResponseEntity<?> getAllProjectMaster();

    ResponseEntity<?> getProjectMasters();

    ResponseEntity<?> getProjectMasterById(Long id);

    ResponseEntity<?> createProjectMaster(@Valid ProjectMaster projectMaster);

    ResponseEntity<?> updateProjectMaster(@Valid ProjectMaster projectMaster);

    ResponseEntity<?> toggleProjectMasterStatus(Long id);

    
}
