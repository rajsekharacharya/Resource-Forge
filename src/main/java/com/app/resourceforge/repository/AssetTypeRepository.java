package com.app.resourceforge.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.resourceforge.model.AssetType;
import com.app.resourceforge.model.DTO.AssetTypeDTO;

public interface AssetTypeRepository extends JpaRepository<AssetType, Long> {

        List<AssetType> findByStatusAndCompanyIdAndSuperCompanyId(boolean status, Integer companyId,
                        Integer superCompanyId);

        List<AssetType> findByCompanyIdAndSuperCompanyId(Integer companyId, Integer superCompanyId);

        boolean existsByTypeAndCompanyIdAndSuperCompanyId(String type, Integer companyId, Integer superCompanyId);

        Optional<AssetType> findByTypeAndCompanyIdAndSuperCompanyId(String type, Integer companyId,
                        Integer superCompanyId);

        @Query(value = "SELECT b.caption FROM asset_type a JOIN asset_type_detail b ON (a.id = b.detail_id) WHERE a.type = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 ORDER BY b.model", nativeQuery = true)
        List<String> getAllProperties(String type, Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT b.caption,b.model FROM asset_type a JOIN asset_type_detail b ON (a.id = b.detail_id) WHERE a.type = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 ORDER BY b.model", nativeQuery = true)
        List<AssetTypeDTO> getPropertiesDetails(String type, Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT b.caption, c.value FROM asset_type a JOIN asset_type_detail b ON (a.id = b.detail_id) JOIN asset_type_detail_des c ON (b.id = c.asset_type_detail_id AND b.company_id = c.company_id AND b.super_company_id = c.super_company_id) WHERE a.type = ?1 AND b.type = 'Dropdown' AND a.company_id = ?2 AND a.super_company_id = ?3", nativeQuery = true)
        List<Map<String, Object>> getDropDownDetails(String type, Integer companyId, Integer superCompanyId);

}
