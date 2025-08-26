package com.app.resourceforge.controller;

import java.util.List;

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

import com.app.resourceforge.model.SubLocation;
import com.app.resourceforge.service.SubLocationService;

@RestController
@RequestMapping("/subLocations")
public class SubLocationController {

    @Autowired
    SubLocationService subLocationService;

    @GetMapping(value = "/getAllSubLocation")
    public ResponseEntity<?> getAllSubLocation() {
        return subLocationService.getAllSubLocation();
    }

    @GetMapping(value = "/getSubLocations")
    public ResponseEntity<?> getSubLocations() {
        return subLocationService.getSubLocations();
    }
    @GetMapping(value = "/getSubLocationsByLocationId")
    public ResponseEntity<?> getSubLocationsByLocationId(@RequestParam Long id) {
        return subLocationService.getSubLocationsByLocationId(id);
    }

    @GetMapping(value = "/getSubLocationById")
    public ResponseEntity<?> getSubLocationById(@RequestParam Long id) {
        return subLocationService.getSubLocationById(id);
    }

    @PostMapping(value = "/createSubLocation")
    public ResponseEntity<?> createSubLocation(@RequestBody @Valid List<SubLocation> subLocation) {
        return subLocationService.createSubLocation(subLocation);
    }

    @PutMapping(value = "/updateSubLocation")
    public ResponseEntity<?> updateSubLocation(@RequestBody @Valid SubLocation subLocation) {
        return subLocationService.updateSubLocation(subLocation);
    }

    @DeleteMapping(value = "/toggleSubLocationStatus")
    public ResponseEntity<?> toggleSubLocationStatus(@RequestParam Long id) {
        return subLocationService.toggleSubLocationStatus(id);
    }

}
