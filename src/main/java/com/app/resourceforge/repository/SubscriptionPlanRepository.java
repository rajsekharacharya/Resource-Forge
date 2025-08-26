package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.SubscriptionPlan;
import java.util.List;
import java.util.Optional;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {

    List<SubscriptionPlan> findBySuperCompanyId(Integer superCompanyId);

    Optional<SubscriptionPlan> findByStatus(boolean status);

    Optional<SubscriptionPlan> findByStatusAndSuperCompanyId(boolean status, Integer superCompanyId);

    List<SubscriptionPlan> findBySubsctptionMasterPlanId(Integer subsctptionMasterPlanId);

    Optional<SubscriptionPlan> findByPlanRequest(boolean planRequest);

    Optional<SubscriptionPlan> findByPlanRequestAndSuperCompanyId(boolean planRequest, Integer superCompanyId);

    List<SubscriptionPlan> findByStatusAndPlanRequest(boolean status, boolean planRequest);

    boolean existsByStatus(boolean status);

    boolean existsByStatusAndSuperCompanyId(boolean status, Integer superCompanyId);

    

}
