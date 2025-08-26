package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.SubscriptionPlanMaster;

public interface SubscriptionPlanMasterService {

    ResponseEntity<?> getSubscriptionPlanMaster();

    ResponseEntity<?> getSubscriptionPlanMasterById(Integer id);

    ResponseEntity<?> createSubscriptionPlanMaster(@Valid SubscriptionPlanMaster subscriptionPlanMaster);

    ResponseEntity<?> updateSubscriptionPlanMaster(@Valid SubscriptionPlanMaster subscriptionPlanMaster);
    
}
