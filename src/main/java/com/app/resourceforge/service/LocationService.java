package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.Location;

public interface LocationService {

    ResponseEntity<?> getAllLocations();

    ResponseEntity<?> getLocationById(Long id);

    ResponseEntity<?> createLocation(@Valid Location location);

    ResponseEntity<?> updateLocation(@Valid Location location);

    ResponseEntity<?> toggleLocationStatus(Long id);

    ResponseEntity<?> getLocations();
    
}
