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

import com.app.resourceforge.model.JobRole;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.DepartmentRepository;
import com.app.resourceforge.repository.JobRoleRepository;
import com.app.resourceforge.service.JobRoleService;

@Service
public class JobRoleServiceImpl implements JobRoleService {

    @Autowired
    private JobRoleRepository jobRoleRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<?> getAllJobRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobRoleRepository.findByCompanyId(user.getCompanyId()));

    }

    @Override
    public ResponseEntity<?> getJobRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(jobRoleRepository.findByStatusAndCompanyId(true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getJobRoleById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobRoleRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createJobRole(@Valid JobRole jobRole) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        if (jobRoleRepository.existsByCompanyIdAndNameAndDepId(user.getCompanyId(), jobRole.getName(),
                jobRole.getDepId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            jobRole.setCompanyId(user.getCompanyId());
            jobRole.setSuperCompanyId(user.getSuperCompanyId());
            jobRole.setStatus(true);
            jobRoleRepository.save(jobRole);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> updateJobRole(@Valid JobRole jobRole) {
        jobRoleRepository.save(jobRole);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> toggleJobRoleStatus(Long id) {
        Optional<JobRole> jobRole = jobRoleRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = jobRole
                .map(role -> {
                    role.setStatus(!role.isStatus());
                    jobRoleRepository.save(role);
                    return messages.get(role.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
