package com.app.resourceforge.controller;

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

import com.app.resourceforge.model.Company;
import com.app.resourceforge.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @GetMapping(value = "/getAllCompanies")
    public ResponseEntity<?> getAllCompanies() {
        return companyService.getAllCompanies();
    }
    @GetMapping(value = "/getTotalCompany")
    public ResponseEntity<?> getTotalCompany() {
        return companyService.getTotalCompany();
    }

    @GetMapping(value = "/getCompanyById")
    public ResponseEntity<?> getCompanyById(@RequestParam Integer id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping(value = "/createCompany", consumes = "application/json")
    public ResponseEntity<?> createCompany(@RequestBody @Valid Company company) {
        return companyService.createCompany(company);
    }

    @PutMapping(value = "/updateCompany", consumes = "application/json")
    public ResponseEntity<?> updateCompany(@RequestBody @Valid Company company) {
        return companyService.updateCompany(company);
    }

    @DeleteMapping(value = "/deleteCompany")
    public ResponseEntity<?> deleteCompany(@RequestParam Integer id) {
        return companyService.deleteCompany(id);
    }
}