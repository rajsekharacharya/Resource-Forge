package com.app.resourceforge.service;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.Employees;

public interface EmployeesService {

    ResponseEntity<?> getAllEmployee();

    ResponseEntity<?> getEmployees();

    ResponseEntity<?> getEmployeeById(Long id);

    ResponseEntity<?> createEmployee( Employees employee);

    ResponseEntity<?> updateEmployee(Employees employee);

    ResponseEntity<?> toggleEmployeeStatus(Long id);

    ResponseEntity<?> getEmployeeByEmpId(String empId);

    ResponseEntity<?> getEmployeesForAssetManagement(Long id);
    
}
