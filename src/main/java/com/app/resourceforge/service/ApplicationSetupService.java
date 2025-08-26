package com.app.resourceforge.service;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.ApplicationSetup;

public interface ApplicationSetupService {

    ResponseEntity<?> getAllApplicationSetups();

    ResponseEntity<?> getApplicationSetupById(Integer id);

    ResponseEntity<?> createApplicationSetup(ApplicationSetup applicationSetup);

    ResponseEntity<?> updateApplicationSetup(ApplicationSetup applicationSetup);

    ResponseEntity<?> deleteApplicationSetup(Integer id);

}
