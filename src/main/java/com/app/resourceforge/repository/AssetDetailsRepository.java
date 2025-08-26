package com.app.resourceforge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.AssetDetails;

@Repository
public interface AssetDetailsRepository extends JpaRepository<AssetDetails, Long> {

    @Query(value = "SELECT * FROM asset_details where asset_id=?1 and company_id=?2 and super_company_id=?3 ", nativeQuery = true)
    Optional<AssetDetails> getDetailsByAssetId(Long assetId,Integer companyId, Integer superCompanyId);

}
