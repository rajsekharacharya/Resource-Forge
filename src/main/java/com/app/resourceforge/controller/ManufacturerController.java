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

import com.app.resourceforge.model.Manufacturer;
import com.app.resourceforge.service.ManufacturerService;

@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping(value = "/getAllManufacturer")
    public ResponseEntity<?> getAllManufacturer() {
        return manufacturerService.getAllManufacturer();
    }

    @GetMapping(value = "/getManufacturers")
    public ResponseEntity<?> getManufacturers() {
        return manufacturerService.getManufacturers();
    }

    @GetMapping(value = "/getManufacturerById")
    public ResponseEntity<?> getManufacturerById(@RequestParam Long id) {
        return manufacturerService.getManufacturerById(id);
    }

    @PostMapping(value = "/createManufacturer")
    public ResponseEntity<?> createManufacturer(@RequestBody @Valid Manufacturer manufacturer) {
        return manufacturerService.createManufacturer(manufacturer);
    }

    @PutMapping(value = "/updateManufacturer")
    public ResponseEntity<?> updateManufacturer(@RequestBody @Valid Manufacturer manufacturer) {
        return manufacturerService.updateManufacturer(manufacturer);
    }

    @DeleteMapping(value = "/toggleManufacturerStatus")
    public ResponseEntity<?> toggleManufacturerStatus(@RequestParam Long id) {
        return manufacturerService.toggleManufacturerStatus(id);
    }
}
