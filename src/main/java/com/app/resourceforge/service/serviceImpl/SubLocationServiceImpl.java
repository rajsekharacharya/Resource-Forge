package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
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
import com.app.resourceforge.model.SubLocation;
import com.app.resourceforge.repository.LocationRepository;
import com.app.resourceforge.repository.SubLocationRepository;
import com.app.resourceforge.service.SubLocationService;

@Service
public class SubLocationServiceImpl implements SubLocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    SubLocationRepository subLocationRepository;

    @Override
    public ResponseEntity<?> getAllSubLocation() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subLocationRepository.findByCompanyIdAndSuperCompanyId(user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getSubLocations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subLocationRepository.findByCompanyIdAndSuperCompanyIdAndStatus(user.getCompanyId(),
                        user.getSuperCompanyId(), true));
    }

    @Override
    public ResponseEntity<?> getSubLocationById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subLocationRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createSubLocation(@Valid List<SubLocation> subLocation) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Location> findById = locationRepository.findById(subLocation.get(0).getLocId());
        subLocation.forEach(x -> {
            x.setCompanyId(user.getCompanyId());
            x.setSuperCompanyId(user.getSuperCompanyId());
            x.setLocation(findById.get());
            x.setStatus(true);
            subLocationRepository.save(x);
        });
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> updateSubLocation(@Valid SubLocation subLocation) {
        subLocationRepository.save(subLocation);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> toggleSubLocationStatus(Long id) {
        Optional<SubLocation> subLocation = subLocationRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = subLocation
                .map(loc -> {
                    loc.setStatus(!loc.isStatus());
                    subLocationRepository.save(loc);
                    return messages.get(loc.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

    @Override
    public ResponseEntity<?> getSubLocationsByLocationId(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(subLocationRepository.findByLocIdAndCompanyIdAndSuperCompanyIdAndStatus(id, user.getCompanyId(), user.getSuperCompanyId(), true));
    }

}
