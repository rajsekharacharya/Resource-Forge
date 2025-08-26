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

import com.app.resourceforge.model.Department;
import com.app.resourceforge.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping(value = "/getAllDepartment")
    public ResponseEntity<?> getAllDepartment() {
        return departmentService.getAllDepartment();
    }
    @GetMapping(value = "/getDepartments")
    public ResponseEntity<?> getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping(value = "/getDepartmentById")
    public ResponseEntity<?> getDepartmentById(@RequestParam Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping(value = "/createDepartment")
    public ResponseEntity<?> createDepartment(@RequestBody @Valid Department department) {
        return departmentService.createDepartment(department);
    }

    @PutMapping(value = "/updateDepartment")
    public ResponseEntity<?> updateDepartment(@RequestBody @Valid Department department) {
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping(value = "/toggleDepartmentStatus")
    public ResponseEntity<?> toggleDepartmentStatus(@RequestParam Long id) {
        return departmentService.toggleDepartmentStatus(id);
    }
    
}
