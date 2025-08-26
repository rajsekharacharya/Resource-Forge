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

import com.app.resourceforge.model.Manufacturer;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.ManufacturerRepository;
import com.app.resourceforge.service.ManufacturerService;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Override
    public ResponseEntity<?> getAllManufacturer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(manufacturerRepository.findByCompanyId(user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getManufacturers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(manufacturerRepository.findByStatusAndCompanyId(true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getManufacturerById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(manufacturerRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createManufacturer(@Valid Manufacturer manufacturer) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (manufacturerRepository.existsByNameAndCompanyId(manufacturer.getName(),user.getCompanyId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            manufacturer.setCompanyId(user.getCompanyId());
            manufacturer.setSuperCompanyId(user.getSuperCompanyId());
            manufacturer.setStatus(true);
            manufacturerRepository.save(manufacturer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> updateManufacturer(@Valid Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleManufacturerStatus(Long id) {
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = manufacturer
                .map(manu -> {
                    manu.setStatus(!manu.isStatus());
                    manufacturerRepository.save(manu);
                    return messages.get(manu.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
