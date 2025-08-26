package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.Location;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.LocationRepository;
import com.app.resourceforge.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public ResponseEntity<?> getAllLocations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(locationRepository.findByCompanyId(user.getCompanyId()));

    }

    @Override
    public ResponseEntity<?> getLocations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(locationRepository.findByStatusAndCompanyId(true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getLocationById(Long id) {
        return locationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> createLocation(@Valid Location location) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (locationRepository.existsByNameAndCompanyIdAndSuperCompanyId(location.getName(), user.getCompanyId(),
                user.getSuperCompanyId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            location.setCompanyId(user.getCompanyId());
            location.setSuperCompanyId(user.getSuperCompanyId());
            location.setStatus(true);
            locationRepository.save(location);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }

    }

    @Override
    public ResponseEntity<?> updateLocation(@Valid Location location) {
        locationRepository.save(location);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> toggleLocationStatus(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = location
                .map(loc -> {
                    loc.setStatus(!loc.isStatus());
                    ;
                    locationRepository.save(loc);
                    return messages.get(loc.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
