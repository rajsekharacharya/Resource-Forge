package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.Company;

public interface CompanyService {

    public ResponseEntity<?> getAllCompanies();

    public ResponseEntity<?> getTotalCompany();

    ResponseEntity<?> getCompanyById(Integer id);

    ResponseEntity<?> createCompany(@Valid Company company);

    ResponseEntity<?> updateCompany(@Valid Company company);

    ResponseEntity<?> deleteCompany(Integer id);


    
}
