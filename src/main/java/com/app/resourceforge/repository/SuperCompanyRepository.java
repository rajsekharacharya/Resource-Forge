package com.app.resourceforge.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.resourceforge.model.SuperCompany;

public interface SuperCompanyRepository extends JpaRepository<SuperCompany, Integer> {

    @Query(value = "SELECT " +
            "COUNT(DISTINCT sc.id) AS total_companies, " +
            "SUM(CASE WHEN EXISTS (SELECT 1 FROM subscription_plan sp WHERE sp.super_company_id = sc.id AND sp.status = 1) THEN 1 ELSE 0 END) AS active_subscription, "
            +
            "COUNT(DISTINCT CASE WHEN NOT EXISTS (SELECT 1 FROM subscription_plan sp WHERE sp.super_company_id = sc.id AND sp.status = 1) THEN sc.id END) AS inactive_subscription "
            +
            "FROM super_company sc", nativeQuery = true)
    List<Map<String, Object>> getSuperCompanyDetails();

    @Query(value = "SELECT a.name as Company,count(b.id) as assetCount FROM  super_company  a left join asset b on (a.id=b.super_company_id) group by a.name", nativeQuery = true)
    List<Map<String, Object>> getAssetCountPerCompany();

    @Query(value = "SELECT plan_name,SUM(CASE WHEN status = TRUE THEN 1 ELSE 0 END) AS total FROM subscription_plan GROUP BY plan_name", nativeQuery = true)
    List<Map<String, Object>> getPlanSummery();

    List<SuperCompany> findByStatus(boolean status);

}
