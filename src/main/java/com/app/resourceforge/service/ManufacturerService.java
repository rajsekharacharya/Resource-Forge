package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.Manufacturer;

public interface ManufacturerService {

    ResponseEntity<?> getAllManufacturer();

    ResponseEntity<?> getManufacturers();

    ResponseEntity<?> getManufacturerById(Long id);

    ResponseEntity<?> createManufacturer(@Valid Manufacturer manufacturer);

    ResponseEntity<?> updateManufacturer(@Valid Manufacturer manufacturer);

    ResponseEntity<?> toggleManufacturerStatus(Long id);
    
}
