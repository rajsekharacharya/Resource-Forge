package com.app.resourceforge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.AssetTypeDetail;

@Repository
public interface AssetTypeDetailRepository extends JpaRepository<AssetTypeDetail, Long> {

    List<AssetTypeDetail> findByCompanyIdAndSuperCompanyId(Integer companyId, Integer superCompanyId);

    List<AssetTypeDetail> findByStatusAndCompanyIdAndSuperCompanyId(boolean status, Integer companyId,
            Integer superCompanyId);

    @Query(nativeQuery = true, value = "SELECT MAX(model) FROM asset_type_detail where detail_id=?1  and company_id=?2 and super_company_id=?3")
    Optional<String> findMaxModel(Long TypeId, Integer companyId, Integer superCompantId);

}
