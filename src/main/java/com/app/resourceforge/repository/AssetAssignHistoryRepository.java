package com.app.resourceforge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.resourceforge.model.AssetAssignHistory;


public interface AssetAssignHistoryRepository extends JpaRepository<AssetAssignHistory,Long> {

    @Query(value = "SELECT * FROM asset_assign_history WHERE status = TRUE AND company_id = ?1 AND super_company_id = ?2 AND asset_id = ?3" , nativeQuery = true)
    Optional<AssetAssignHistory> findAssign(Integer companyId,Integer superCompanyId,Long assetId);
    
}
