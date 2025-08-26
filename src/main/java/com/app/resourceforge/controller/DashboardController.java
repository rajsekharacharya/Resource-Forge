package com.app.resourceforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.service.DashboardService;

@RestController
@RequestMapping("/dashboardAdmin")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping(value = "/getDashboardForSAAS")
    public ResponseEntity<?> getDashboardForSAAS() {
        return dashboardService.getDashboardForSAAS();
    }

        @GetMapping(value = "/getDashboardForSuperAdmin")
    public ResponseEntity<?> getDashboardForSuperAdmin() {
        return dashboardService.getDashboardForSuperAdmin();
    }

    @GetMapping(value = "/getDashboardForAdmin")
    public ResponseEntity<?> getDashboardForAdmin() {
        return dashboardService.getDashboardForAdmin();
    }
    @GetMapping(value = "/getCompanyAndSuperCompany")
    public ResponseEntity<?> getCompanyAndSuperCompany() {
        return dashboardService.getCompanyAndSuperCompany();
    }

    @GetMapping(value = "/getDashboardForServiceEngineer")
    public ResponseEntity<?> getDashboardForServiceEngineer() {
        return dashboardService.getDashboardForServiceEngineer();
    }
}
