package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.SubscriptionPlanMaster;

public interface SubscriptionPlanMasterRepository extends JpaRepository<SubscriptionPlanMaster,Integer> {
    
}
