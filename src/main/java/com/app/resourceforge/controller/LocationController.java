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

import com.app.resourceforge.model.Location;
import com.app.resourceforge.service.LocationService;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping(value = "/getAllLocations")
    public ResponseEntity<?> getAllLocations() {
        return locationService.getAllLocations();
    }
    @GetMapping(value = "/getLocations")
    public ResponseEntity<?> getLocations() {
        return locationService.getLocations();
    }

    @GetMapping(value = "/getLocationById")
    public ResponseEntity<?> getLocationById(@RequestParam Long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping(value = "/createLocation")
    public ResponseEntity<?> createLocation(@RequestBody @Valid Location location) {
        return locationService.createLocation(location);
    }

    @PutMapping(value = "/updateLocation")
    public ResponseEntity<?> updateLocation(@RequestBody @Valid Location location) {
        return locationService.updateLocation(location);
    }

    @DeleteMapping(value = "/toggleLocationStatus")
    public ResponseEntity<?> toggleLocationStatus(@RequestParam Long id) {
        return locationService.toggleLocationStatus(id);
    }
}
