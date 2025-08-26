package com.app.resourceforge.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.SubLocation;

public interface SubLocationService {

    ResponseEntity<?> getAllSubLocation();

    ResponseEntity<?> getSubLocations();

    ResponseEntity<?> getSubLocationById(Long id);

    ResponseEntity<?> createSubLocation(@Valid List<SubLocation> subLocation);

    ResponseEntity<?> updateSubLocation(@Valid SubLocation subLocation);

    ResponseEntity<?> toggleSubLocationStatus(Long id);

    ResponseEntity<?> getSubLocationsByLocationId(Long id);
    
}
