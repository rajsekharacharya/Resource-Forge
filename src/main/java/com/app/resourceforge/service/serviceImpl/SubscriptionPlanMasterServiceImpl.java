package com.app.resourceforge.service.serviceImpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.model.SubscriptionPlanMaster;
import com.app.resourceforge.repository.SubscriptionPlanMasterRepository;
import com.app.resourceforge.repository.SubscriptionPlanRepository;
import com.app.resourceforge.service.SubscriptionPlanMasterService;

@Service
public class SubscriptionPlanMasterServiceImpl implements SubscriptionPlanMasterService {

    @Autowired
    SubscriptionPlanMasterRepository subscriptionPlanMasterRepository;
    @Autowired
    SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public ResponseEntity<?> getSubscriptionPlanMaster() {
        return ResponseEntity.ok(subscriptionPlanMasterRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getSubscriptionPlanMasterById(Integer id) {
        return ResponseEntity.ok(subscriptionPlanMasterRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createSubscriptionPlanMaster(@Valid SubscriptionPlanMaster subscriptionPlanMaster) {
        subscriptionPlanMaster.setEditable(false);
        subscriptionPlanMasterRepository.save(subscriptionPlanMaster);
        return ResponseEntity.status(HttpStatus.CREATED).body("save");
    }

    @Override
    public ResponseEntity<?> updateSubscriptionPlanMaster(@Valid SubscriptionPlanMaster subscriptionPlanMaster) {
        SubscriptionPlanMaster planMaster = subscriptionPlanMasterRepository.save(subscriptionPlanMaster);
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findBySubsctptionMasterPlanId(planMaster.getId());

        plans.forEach(plan -> {
            plan.setSubsctptionMasterPlanId(planMaster.getId());
            plan.setPlanName(planMaster.getPlanName());
            plan.setAsset(planMaster.getAsset());
            plan.setCompany(planMaster.getCompany());
            plan.setSuperAdmin(planMaster.getSuperAdmin());
            plan.setUser(planMaster.getUser());
            plan.setPrice(planMaster.getPrice());
            plan.setValidity(planMaster.getValidity());
            plan.setMobileAccess(planMaster.isMobileAccess());
            plan.setDocUpload(planMaster.isDocUpload());
            plan.setNote(planMaster.getNote());
            subscriptionPlanRepository.save(plan);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body("save");
    }

}
