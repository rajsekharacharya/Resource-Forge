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

import com.app.resourceforge.model.ApplicationSetup;
import com.app.resourceforge.service.ApplicationSetupService;

@RestController
@RequestMapping("/applicationSetups")
public class ApplicationSetupController {

    private final ApplicationSetupService applicationSetupService;

    public ApplicationSetupController(ApplicationSetupService applicationSetupService) {
        this.applicationSetupService = applicationSetupService;
    }

    @GetMapping(value = "/getAllApplicationSetups")
    public ResponseEntity<?> getAllApplicationSetups() {
        return applicationSetupService.getAllApplicationSetups();
    }

    @GetMapping(value = "/getApplicationSetupById")
    public ResponseEntity<?> getApplicationSetupById(@RequestParam Integer id) {
        return applicationSetupService.getApplicationSetupById(id);
    }

    @PostMapping(value = "/createApplicationSetup", consumes = "application/json")
    public ResponseEntity<?> createApplicationSetup(@RequestBody @Valid ApplicationSetup applicationSetup) {
        return applicationSetupService.createApplicationSetup(applicationSetup);

    }

    @PutMapping(value = "/updateApplicationSetup", consumes = "application/json")
    public ResponseEntity<?> updateApplicationSetup(@RequestBody @Valid ApplicationSetup applicationSetup) {
        return applicationSetupService.createApplicationSetup(applicationSetup);
    }

    @DeleteMapping(value = "/deleteApplicationSetup")
    public ResponseEntity<?> deleteApplicationSetup(@RequestParam Integer id) {
        return applicationSetupService.deleteApplicationSetup(id);
    }
}