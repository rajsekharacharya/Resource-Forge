package com.app.resourceforge.service;

import org.springframework.http.ResponseEntity;

public interface DashboardService {

    ResponseEntity<?> getDashboardForAdmin();

    ResponseEntity<?> getDashboardForSAAS();

    ResponseEntity<?> getDashboardForSuperAdmin();

    ResponseEntity<?> getCompanyAndSuperCompany();

    ResponseEntity<?> getDashboardForServiceEngineer();
 
}
