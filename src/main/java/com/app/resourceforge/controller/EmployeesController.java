package com.app.resourceforge.controller;

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

import com.app.resourceforge.model.Employees;
import com.app.resourceforge.service.EmployeesService;

@RestController
@RequestMapping("/employee")
public class EmployeesController {

    @Autowired
    public EmployeesService employeesService;

    @GetMapping(value = "/getAllEmployee")
    public ResponseEntity<?> getAllEmployee() {
        return employeesService.getAllEmployee();
    }
    @GetMapping(value = "/getEmployees")
    public ResponseEntity<?> getEmployees() {
        return employeesService.getEmployees();
    }
    @GetMapping(value = "/getEmployeesForAssetManagement")
    public ResponseEntity<?> getEmployeesForAssetManagement(@RequestParam(required = false,defaultValue = "0") Long id) {
        return employeesService.getEmployeesForAssetManagement(id);
    }

    @GetMapping(value = "/getEmployeeById")
    public ResponseEntity<?> getEmployeeById(@RequestParam Long id) {
        return employeesService.getEmployeeById(id);
    }

    @GetMapping(value = "/getEmployeeByEmpId")
    public ResponseEntity<?> getEmployeeByEmpId(@RequestParam String empId) {
        return employeesService.getEmployeeByEmpId(empId);
    }

    @PostMapping(value = "/createEmployee")
    public ResponseEntity<?> createEmployee(@RequestBody Employees employee) {
        return employeesService.createEmployee(employee);
    }

    @PutMapping(value = "/updateEmployee")
    public ResponseEntity<?> updateEmployee(@RequestBody Employees employee) {
        return employeesService.updateEmployee(employee);
    }

    @DeleteMapping(value = "/toggleEmployeeStatus")
    public ResponseEntity<?> toggleEmployeeStatus(@RequestParam Long id) {
        return employeesService.toggleEmployeeStatus(id);
    }
    
}
