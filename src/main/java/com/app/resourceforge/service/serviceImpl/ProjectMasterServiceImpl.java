package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.ProjectMaster;
import com.app.resourceforge.repository.ProjectMasterRepository;
import com.app.resourceforge.service.ProjectMasterService;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService {

    @Autowired
    ProjectMasterRepository projectMasterRepository;

    @Override
    public ResponseEntity<?> getAllProjectMaster() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectMasterRepository.findByCompanyId(user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getProjectMasters() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectMasterRepository.findByStatusAndCompanyId(true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getProjectMasterById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMasterRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createProjectMaster(@Valid ProjectMaster projectMaster) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (projectMasterRepository.existsByNameAndCompanyId(projectMaster.getName(), user.getCompanyId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            projectMaster.setCompanyId(user.getCompanyId());
            projectMaster.setSuperCompanyId(user.getSuperCompanyId());
            projectMaster.setStatus(true);
            projectMasterRepository.save(projectMaster);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> updateProjectMaster(@Valid ProjectMaster projectMaster) {
        projectMasterRepository.save(projectMaster);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleProjectMasterStatus(Long id) {
        Optional<ProjectMaster> project = projectMasterRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = project
                .map(manu -> {
                    manu.setStatus(!manu.isStatus());
                    projectMasterRepository.save(manu);
                    return messages.get(manu.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
