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

import com.app.resourceforge.model.SubscriptionPlanMaster;
import com.app.resourceforge.service.SubscriptionPlanMasterService;

@RestController
@RequestMapping("/subscription-plan-master")
public class SubscriptionPlanMasterController {

    @Autowired
    private SubscriptionPlanMasterService subscriptionPlanMasterService;

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getSubscriptionPlanMaster")
    public ResponseEntity<?> getSubscriptionPlanMaster() {
        return subscriptionPlanMasterService.getSubscriptionPlanMaster();
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @GetMapping(value = "/getSubscriptionPlanMasterById")
    public ResponseEntity<?> getSubscriptionPlanMasterById(@RequestParam Integer id) {
        return subscriptionPlanMasterService.getSubscriptionPlanMasterById(id);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PostMapping(value = "/createSubscriptionPlanMaster")
    public ResponseEntity<?> createSubscriptionPlanMaster(
            @RequestBody @Valid SubscriptionPlanMaster subscriptionPlanMaster) {
        return subscriptionPlanMasterService.createSubscriptionPlanMaster(subscriptionPlanMaster);
    }

    @PreAuthorize("hasAuthority('VARELI')")
    @PutMapping(value = "/updateSubscriptionPlanMaster")
    public ResponseEntity<?> updateSubscriptionPlanMaster(
            @RequestBody @Valid SubscriptionPlanMaster subscriptionPlanMaster) {
        return subscriptionPlanMasterService.updateSubscriptionPlanMaster(subscriptionPlanMaster);
    }

}
