package com.app.resourceforge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.Settings;
import com.app.resourceforge.repository.SettingsRepository;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    SettingsRepository settingsRepository;

    @GetMapping(value = "/getAllSettings")
    public ResponseEntity<?> getAllSettings() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(settingsRepository.findBySuperCompanyId(user.getSuperCompanyId()));
    }

    @GetMapping(value = "/getSettingsById")
    public ResponseEntity<?> getSettingsById(@RequestParam Integer id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(settingsRepository.findById(id));
    }

    @PostMapping(value = "/createSettings", consumes = "application/json")
    public ResponseEntity<?> createSettings(@RequestBody @Valid Settings settings) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        settings.setSuperCompanyId(user.getSuperCompanyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(settingsRepository.save(settings));

    }

    @PutMapping(value = "/updateSettings", consumes = "application/json")
    public ResponseEntity<?> updateSettings(@RequestBody @Valid Settings settings) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        settings.setSuperCompanyId(user.getSuperCompanyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(settingsRepository.save(settings));
    }

    @DeleteMapping(value = "/deleteSettings")
    public ResponseEntity<?> deleteSettings(@RequestParam Integer id) {
        settingsRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Deleted");
    }

}
