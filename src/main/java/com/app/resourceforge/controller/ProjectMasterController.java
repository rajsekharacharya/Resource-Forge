package com.app.resourceforge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.ProjectMaster;
import com.app.resourceforge.service.ProjectMasterService;

@RestController
@RequestMapping("/projectMaster")
public class ProjectMasterController {

    @Autowired
    ProjectMasterService projectMasterService;

    @GetMapping(value = "/getAllProjectMaster")
    public ResponseEntity<?> getAllProjectMaster() {
        return projectMasterService.getAllProjectMaster();
    }

    @GetMapping(value = "/getProjectMasters")
    public ResponseEntity<?> getProjectMasters() {
        return projectMasterService.getProjectMasters();
    }

    @GetMapping(value = "/getProjectMasterById")
    public ResponseEntity<?> getProjectMasterById(@RequestParam Long id) {
        return projectMasterService.getProjectMasterById(id);
    }

    @PostMapping(value = "/createProjectMaster")
    public ResponseEntity<?> createProjectMaster(@RequestBody @Valid ProjectMaster projectMaster) {
        return projectMasterService.createProjectMaster(projectMaster);
    }

    @PutMapping(value = "/updateProjectMaster")
    public ResponseEntity<?> updateProjectMaster(@RequestBody @Valid ProjectMaster projectMaster) {
        return projectMasterService.updateProjectMaster(projectMaster);
    }

    @DeleteMapping(value = "/toggleProjectMasterStatus")
    public ResponseEntity<?> toggleProjectMasterStatus(@RequestParam Long id) {
        return projectMasterService.toggleProjectMasterStatus(id);
    }

}
