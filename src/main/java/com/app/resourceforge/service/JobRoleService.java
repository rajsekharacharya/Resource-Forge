package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.JobRole;

public interface JobRoleService {

    ResponseEntity<?> getAllJobRole();

    ResponseEntity<?> getJobRoles();

    ResponseEntity<?> getJobRoleById(Long id);

    ResponseEntity<?> createJobRole(@Valid JobRole jobRole);

    ResponseEntity<?> updateJobRole(@Valid JobRole jobRole);

    ResponseEntity<?> toggleJobRoleStatus(Long id);
    
}
