package com.app.resourceforge.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.JobRole;
import com.app.resourceforge.service.JobRoleService;

@RestController
@RequestMapping("/jobRole")
public class JobRoleController {

    private final JobRoleService jobRoleService;

    public JobRoleController(JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    @GetMapping(value = "/getAllJobRole")
    public ResponseEntity<?> getAllJobRole() {
        return jobRoleService.getAllJobRole();
    }

    @GetMapping(value = "/getJobRoles")
    public ResponseEntity<?> getJobRoles() {
        return jobRoleService.getJobRoles();
    }

    @GetMapping(value = "/getJobRoleById")
    public ResponseEntity<?> getJobRoleById(@RequestParam Long id) {
        return jobRoleService.getJobRoleById(id);
    }

    @PostMapping(value = "/createJobRole")
    public ResponseEntity<?> createJobRole(@RequestBody @Valid JobRole jobRole) {
        return jobRoleService.createJobRole(jobRole);
    }

    @PutMapping(value = "/updateJobRole")
    public ResponseEntity<?> updateJobRole(@RequestBody @Valid JobRole jobRole) {
        return jobRoleService.updateJobRole(jobRole);
    }

    @DeleteMapping(value = "/toggleJobRoleStatus")
    public ResponseEntity<?> toggleJobRoleStatus(@RequestParam Long id) {
        return jobRoleService.toggleJobRoleStatus(id);
    }
}
