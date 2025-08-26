package com.app.resourceforge.autoService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.resourceforge.repository.SubscriptionPlanRepository;

@Service
public class SubscriptionPlanUpdate {

    @Autowired
    SubscriptionPlanRepository subscriptionPlanRepository;

    @Scheduled(cron = "0 0 1 * * ?")
    public void ScheduledSubscriptionPlan() {
        subscriptionPlanRepository.findByStatus(true).ifPresent(plan -> {
            if (plan.getEndDate() != null && plan.getEndDate().isBefore(LocalDate.now())) {
                plan.setStatus(false);
                plan.setStartDate(null);
                plan.setEndDate(null);
                subscriptionPlanRepository.save(plan);
            }
        });
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void NotifySubscriptionPlanExpriy() {
        subscriptionPlanRepository.findByStatus(true)
                .ifPresent(plan -> {
                    if (plan.getEndDate().minusDays(2).equals(LocalDate.now())) {
                        System.out.println("Please Recharge");
                    }
                });

    }

}
