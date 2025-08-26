package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.Department;

public interface DepartmentService {

    ResponseEntity<?> getAllDepartment();

    ResponseEntity<?> getDepartments();

    ResponseEntity<?> getDepartmentById(Long id);

    ResponseEntity<?> createDepartment(@Valid Department department);

    ResponseEntity<?> updateDepartment(@Valid Department department);

    ResponseEntity<?> toggleDepartmentStatus(Long id);

}
