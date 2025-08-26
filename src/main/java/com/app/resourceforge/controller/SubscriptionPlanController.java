package com.app.resourceforge.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.service.SubscriptionPlanService;

@RestController
@RequestMapping("/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    @PreAuthorize("hasAnyAuthority('VARELI','SUPERADMIN')")
    @GetMapping(value = "/getSubscriptionPlan")
    public ResponseEntity<?> getSubscriptionPlan() {
        return subscriptionPlanService.getSubscriptionPlan();
    }
    @PreAuthorize("hasAnyAuthority('VARELI','SUPERADMIN')")
    @GetMapping(value = "/getSubscriptionPlanByCompany")
    public ResponseEntity<?> getSubscriptionPlanByCompany(@RequestParam Integer id) {
        return subscriptionPlanService.getSubscriptionPlanByCompany(id);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getSubscriptionPlanById")
    public ResponseEntity<?> getSubscriptionPlanById(@RequestParam Integer id) {
        return subscriptionPlanService.getSubscriptionPlanById(id);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PostMapping(value = "/createSubscriptionPlan")
    public ResponseEntity<?> createSubscriptionPlan(@RequestBody @Valid SubscriptionPlan subscriptionPlan) {
        return subscriptionPlanService.createSubscriptionPlan(subscriptionPlan);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PutMapping(value = "/updateSubscriptionPlan")
    public ResponseEntity<?> updateSubscriptionPlan(@RequestBody @Valid SubscriptionPlan subscriptionPlan) {
        return subscriptionPlanService.updateSubscriptionPlan(subscriptionPlan);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @DeleteMapping(value = "/deleteSubscriptionPlan")
    public ResponseEntity<?> deleteSubscriptionPlan(@RequestParam Integer id) {
        return subscriptionPlanService.deleteSubscriptionPlan(id);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/subscriptionPlanToggle")
    public ResponseEntity<?> subscriptionPlanToggle(@RequestParam Integer id) {
        return subscriptionPlanService.subscriptionPlanToggle(id);
    }

    @PreAuthorize("hasAnyAuthority('VARELI','SUPERADMIN')")
    @GetMapping(value = "/requestSubscriptionPlanToggle")
    public ResponseEntity<?> requestSubscriptionPlanToggle(@RequestParam Integer id) {
        return subscriptionPlanService.requestSubscriptionPlanToggle(id);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/subscriptionPlanExtension")
    public ResponseEntity<?> subscriptionPlanExtension(@RequestParam Integer id,@RequestParam Long year) {
        return subscriptionPlanService.subscriptionPlanExtension(id,year);
    }

    
    @GetMapping(value = "/activeSubscriptionPlan")
    public Boolean activeSubscriptionPlan() {
        return subscriptionPlanService.activeSubscriptionPlan();
    }

}
