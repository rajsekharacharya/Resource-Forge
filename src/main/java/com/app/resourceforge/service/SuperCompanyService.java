package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.SuperCompany;

public interface SuperCompanyService {

    ResponseEntity<?> getAllSuperCompanies();

    ResponseEntity<?> getTotalSuperCompany();

    ResponseEntity<?> getSuperCompanyById(Integer id);

    ResponseEntity<?> createSuperCompany(@Valid SuperCompany company);

    ResponseEntity<?> updateSuperCompany(@Valid SuperCompany company);

    ResponseEntity<?> toggleSuperCompany(Integer id);

    ResponseEntity<?> getSuperCompanies();

}
