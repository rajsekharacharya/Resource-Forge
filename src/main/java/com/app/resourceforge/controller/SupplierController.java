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

import com.app.resourceforge.model.Supplier;
import com.app.resourceforge.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping(value = "/getAllSupplier")
    public ResponseEntity<?> getAllSupplier() {
        return supplierService.getAllSupplier();
    }

    @GetMapping(value = "/getSuppliers")
    public ResponseEntity<?> getSuppliers() {
        return supplierService.getSuppliers();
    }
    @GetMapping(value = "/getServies")
    public ResponseEntity<?> getServies() {
        return supplierService.getServies();
    }

    @GetMapping(value = "/getSupplierById")
    public ResponseEntity<?> getSupplierById(@RequestParam Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping(value = "/createSupplier")
    public ResponseEntity<?> createSupplier(@RequestBody @Valid Supplier supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PutMapping(value = "/updateSupplier")
    public ResponseEntity<?> updateSupplier(@RequestBody @Valid Supplier supplier) {
        return supplierService.updateSupplier(supplier);
    }

    @DeleteMapping(value = "/toggleSupplierStatus")
    public ResponseEntity<?> toggleSupplierStatus(@RequestParam Long id) {
        return supplierService.toggleSupplierStatus(id);
    }

}
