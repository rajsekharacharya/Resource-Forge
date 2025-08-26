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

import com.app.resourceforge.model.Department;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.DepartmentRepository;
import com.app.resourceforge.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<?> getAllDepartment() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentRepository.findByCompanyIdAndSuperCompanyIdOrderByNameAsc(user.getCompanyId(),user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getDepartments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentRepository.findByStatusAndCompanyIdAndSuperCompanyIdOrderByNameAsc(true, user.getCompanyId(),user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getDepartmentById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createDepartment(@Valid Department department) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (departmentRepository.existsByNameAndCompanyIdAndSuperCompanyId(department.getName(),user.getCompanyId(),user.getSuperCompanyId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            department.setCompanyId(user.getCompanyId());
            department.setSuperCompanyId(user.getSuperCompanyId());
            department.setStatus(true);
            departmentRepository.save(department);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }

    }

    @Override
    public ResponseEntity<?> updateDepartment(@Valid Department department) {
        departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleDepartmentStatus(Long id) {
        Optional<Department> department = departmentRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = department
                .map(dept -> {
                    dept.setStatus(!dept.isStatus());
                    departmentRepository.save(dept);
                    return messages.get(dept.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
