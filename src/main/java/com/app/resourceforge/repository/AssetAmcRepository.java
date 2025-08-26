package com.app.resourceforge.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.AssetAmc;
import com.app.resourceforge.model.AssetInsurance;

@Repository
public interface AssetAmcRepository extends JpaRepository<AssetAmc, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM asset a WHERE DATEDIFF(a.warranty_end_date, ?1) < 0;")
    List<Asset> getExpiredWarranty(String date);

    @Query(nativeQuery = true, value = "SELECT * FROM asset_amc a WHERE DATEDIFF(a.end_date, ?1) < 0 ;")
    List<AssetAmc> getExpiredAmc(String date);

    @Query(nativeQuery = true, value = "SELECT * FROM asset_insurance a WHERE DATEDIFF(a.end_date, ?1) < 0 ;")
    List<AssetInsurance> getExpiredInsurance(String date);

    @Query(value = "SELECT type, asset_tag_id, serial_no, model, warranty_start_date, warranty_end_date, DATEDIFF(warranty_end_date, ?1) as remainingDays "
            + "FROM asset WHERE DATEDIFF(warranty_end_date, ?1) > 0 AND warranty_status = true AND company_id = ?2 AND super_company_id = ?3", nativeQuery = true)
    List<Map<String, Object>> findAssetsWithRemainingWarranty(String date, Integer companyId, Integer superCompanyId);

    @Query(value = "SELECT a.type, a.asset_tag_id, a.serial_no, a.model, b.start_date, b.end_date, DATEDIFF(b.end_date, ?1) as remainingDays "
            +
            "FROM asset a " +
            "JOIN asset_amc b ON (a.id = b.asset_id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
            +
            "WHERE DATEDIFF(b.end_date, ?1) > 0 AND b.status=true AND a.company_id = ?2 AND a.super_company_id = ?3", nativeQuery = true)
    List<Map<String, Object>> findAssetsWithRemainingAmc(String date, Integer companyId, Integer superCompanyId);

    @Query(value = "SELECT a.type, a.asset_tag_id, a.serial_no, a.model, b.start_date, b.end_date, DATEDIFF(b.end_date, ?1) as remainingDays "
            +
            "FROM asset a " +
            "JOIN asset_insurance b ON (a.id = b.asset_id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
            +
            "WHERE DATEDIFF(b.end_date, ?1) > 0 AND b.status=true AND a.company_id = ?2 AND a.super_company_id = ?3", nativeQuery = true)
    List<Map<String, Object>> findAssetsWithRemainingInsurance(String date, Integer companyId, Integer superCompanyId);

}
