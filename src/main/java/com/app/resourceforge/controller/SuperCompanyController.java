package com.app.resourceforge.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.SuperCompany;
import com.app.resourceforge.service.SuperCompanyService;

@RestController
@RequestMapping("/SuperCompanies")
public class SuperCompanyController {
    @Autowired
    private SuperCompanyService superCompanyService;

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getAllSuperCompanies")
    public ResponseEntity<?> getAllSuperCompanies() {
        return superCompanyService.getAllSuperCompanies();
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getSuperCompanies")
    public ResponseEntity<?> getSuperCompanies() {
        return superCompanyService.getSuperCompanies();
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getTotalSuperCompany")
    public ResponseEntity<?> getTotalSuperCompany() {
        return superCompanyService.getTotalSuperCompany();
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getSuperCompanyById")
    public ResponseEntity<?> getSuperCompanyById(@RequestParam Integer id) {
        return superCompanyService.getSuperCompanyById(id);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PostMapping(value = "/createSuperCompany", consumes = "application/json")
    public ResponseEntity<?> createSuperCompany(@RequestBody @Valid SuperCompany company) {
        return superCompanyService.createSuperCompany(company);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PutMapping(value = "/updateSuperCompany", consumes = "application/json")
    public ResponseEntity<?> updateSuperCompany(@RequestBody @Valid SuperCompany company) {
        return superCompanyService.updateSuperCompany(company);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PutMapping(value = "/toggleSuperCompany")
    public ResponseEntity<?> toggleSuperCompany(@RequestParam Integer id) {
        return superCompanyService.toggleSuperCompany(id);
    }
}
