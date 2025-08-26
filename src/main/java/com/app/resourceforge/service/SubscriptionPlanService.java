package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.SubscriptionPlan;

public interface SubscriptionPlanService {

    ResponseEntity<?> getSubscriptionPlan();

    ResponseEntity<?> getSubscriptionPlanByCompany(Integer id);

    ResponseEntity<?> getSubscriptionPlanById(Integer id);

    ResponseEntity<?> createSubscriptionPlan(@Valid SubscriptionPlan subscriptionPlan);

    ResponseEntity<?> updateSubscriptionPlan(@Valid SubscriptionPlan subscriptionPlan);

    ResponseEntity<?> deleteSubscriptionPlan(Integer id);

    ResponseEntity<?> subscriptionPlanToggle(Integer id);

    ResponseEntity<?> requestSubscriptionPlanToggle(Integer id);

    ResponseEntity<?> subscriptionPlanExtension(Integer id, Long year);

    Boolean activeSubscriptionPlan();

}
