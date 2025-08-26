package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.Supplier;

public interface SupplierService {

    ResponseEntity<?> getAllSupplier();

    ResponseEntity<?> getSuppliers();

    ResponseEntity<?> getSupplierById(Long id);

    ResponseEntity<?> createSupplier(@Valid Supplier supplier);

    ResponseEntity<?> updateSupplier(@Valid Supplier supplier);

    ResponseEntity<?> toggleSupplierStatus(Long id);

    ResponseEntity<?> getServies();
    
}
