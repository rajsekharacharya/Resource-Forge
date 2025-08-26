package com.app.resourceforge.service.serviceImpl;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.repository.SubscriptionPlanRepository;
import com.app.resourceforge.service.SubscriptionPlanService;

@Service
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    @Autowired
    SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public ResponseEntity<?> getSubscriptionPlan() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(subscriptionPlanRepository.findBySuperCompanyId(user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getSubscriptionPlanByCompany(Integer id) {
        return ResponseEntity.ok(subscriptionPlanRepository.findBySuperCompanyId(id));
    }

    @Override
    public ResponseEntity<?> getSubscriptionPlanById(Integer id) {
        return subscriptionPlanRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> createSubscriptionPlan(@Valid SubscriptionPlan subscriptionPlan) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        subscriptionPlan.setStatus(false);
        subscriptionPlan.setPlanRequest(false);
        subscriptionPlan.setSuperCompanyId(user.getSuperCompanyId());
        subscriptionPlan.setSubsctptionMasterPlanId(0);
        SubscriptionPlan save = subscriptionPlanRepository.save(subscriptionPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @Override
    public ResponseEntity<?> updateSubscriptionPlan(@Valid SubscriptionPlan subscriptionPlan) {
        subscriptionPlanRepository.save(subscriptionPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> deleteSubscriptionPlan(Integer id) {
        SubscriptionPlan existingSubscriptionPlan = subscriptionPlanRepository.findById(id).orElse(null);
        if (existingSubscriptionPlan != null) {
            subscriptionPlanRepository.delete(existingSubscriptionPlan);
            return ResponseEntity.status(HttpStatus.CREATED).body("Deleted");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Found");
    }

    @Override
    public ResponseEntity<?> subscriptionPlanToggle(Integer id) {
        Optional<SubscriptionPlan> requestPlan = subscriptionPlanRepository.findById(id);
        Optional<SubscriptionPlan> activePlan = subscriptionPlanRepository.findByStatusAndSuperCompanyId(true,
                requestPlan.get().getSuperCompanyId());

        if (activePlan.isPresent() && !activePlan.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Active Plan! Deactivate Please");
        }
        return requestPlan
                .map(plan -> {
                    plan.setStartDate(plan.isStatus() ? null : LocalDate.now());
                    plan.setEndDate(plan.isStatus() ? null : LocalDate.now().plusYears(plan.getValidity()));
                    plan.setStatus(!plan.isStatus());
                    plan.setPlanRequest(false);
                    subscriptionPlanRepository.save(plan);
                    return ResponseEntity.status(HttpStatus.OK).body("Plan Toggled Successfully");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found"));
    }

    @Override
    public ResponseEntity<?> requestSubscriptionPlanToggle(Integer id) {
        Optional<SubscriptionPlan> requestPlan = subscriptionPlanRepository.findById(id);
        Optional<SubscriptionPlan> requesrtPlan = subscriptionPlanRepository.findByPlanRequestAndSuperCompanyId(true,
                requestPlan.get().getSuperCompanyId());

        if (requesrtPlan.isPresent() && !requesrtPlan.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already Requested! Please Deactive");
        }
        return requestPlan
                .map(plan -> {
                    plan.setPlanRequest(!plan.isPlanRequest());
                    subscriptionPlanRepository.save(plan);
                    return ResponseEntity.status(HttpStatus.OK).body("Plan Requested");
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found"));
    }

    @Override
    public ResponseEntity<?> subscriptionPlanExtension(Integer id, Long year) {
        return subscriptionPlanRepository.findById(id)
                .map(plan -> {
                    if (plan.isStatus()) {
                        plan.setEndDate(plan.getEndDate().plusYears(year));
                        subscriptionPlanRepository.save(plan);
                        return ResponseEntity.status(HttpStatus.OK).body("Plan Extended to " + year + " year");
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plan Not Active");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan not found"));
    }

    @Override
    public Boolean activeSubscriptionPlan() {
        return subscriptionPlanRepository.existsByStatus(true);
    }

}
