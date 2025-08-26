package com.app.resourceforge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.resourceforge.model.AssetMaintenances;

public interface AssetMaintenancesRepository extends JpaRepository<AssetMaintenances, Long> {

    @Query(value = "SELECT * FROM asset_maintenances WHERE status = TRUE AND company_id = ?1 AND super_company_id = ?2 AND asset_id = ?3", nativeQuery = true)
    Optional<AssetMaintenances> findRepair(Integer companyId, Integer superCompanyId, Long assetId);

}
