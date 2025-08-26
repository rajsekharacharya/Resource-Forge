package com.app.resourceforge.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.resourceforge.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

        Optional<Asset> findByUuidAndCompanyIdAndSuperCompanyId(String uuid, Integer companyId,
                        Integer superCompanyId);

        Optional<Asset> findByUuidOrSerialNoOrData09AndCompanyIdAndSuperCompanyId(String uuid, String serialNo,
                        String data09, Integer companyId,
                        Integer superCompanyId);

        @Query(value = "SELECT * FROM asset.asset where company_id = ?4 and super_company_id = ?5 AND  (uuid = ?1 OR serial_no = ?2 OR  data09 = ?3)", nativeQuery = true)
        Optional<Asset> findByCompanyCodeAndAnyIdentifier(String uuid, String serialNo,
                        String data09, Integer companyId,
                        Integer superCompanyId);

        List<Asset> findByCompanyId(Integer companyId);

        List<Asset> findBySerialNoAndCompanyIdAndSuperCompanyId(String serialNo, Integer companyId,
                        Integer superCompanyId);

        Optional<Asset> findByIdAndCompanyIdAndSuperCompanyId(Long id, Integer companyId,
                        Integer superCompanyId);

        List<Asset> findByActiveAndCompanyId(boolean active, Integer companyId);

        List<Asset> findByActiveAndCompanyIdAndSuperCompanyId(boolean active, Integer companyId,
                        Integer superCompanyId);

        boolean existsBySerialNo(String serialNo);

        boolean existsBySerialNoAndCompanyIdAndSuperCompanyId(String serialNo, Integer companyId,
                        Integer superCompanyId);

        Optional<Asset> findByAssetTagIdAndCompanyIdAndSuperCompanyId(String assetTagId, Integer companyId,
                        Integer superCompanyId);

        Integer countBySuperCompanyId(Integer superCompanyId);

        @Query(value = "SELECT a.id, a.asset_tag_id,a.device_name,a.type, a.model, a.serial_no, a.amc_status, a.insurance_status, a.warranty_status, "
                        +
                        "a.finance_details, a.manufacturer, a.supplier, a.status, a.active FROM asset a "
                        +
                        "WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND dismiss = FALSE ", nativeQuery = true)
        List<Map<String, Object>> getAllAssets(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.id, a.type, a.asset_tag_id, a.device_name, a.model, a.serial_no, a.manufacturer, a.supplier, a.status, a.location, a.sub_location, a.reserve_dept, c.employee, e.doc_upload, f.name FROM asset a LEFT JOIN asset_assign_history c ON a.id = c.asset_id AND a.company_id = c.company_id AND a.super_company_id = c.super_company_id AND c.status = TRUE LEFT JOIN subscription_plan e ON a.super_company_id = e.super_company_id AND e.status = TRUE LEFT JOIN asset_customer_assign f ON a.id = f.asset_id AND a.company_id = f.company_id AND a.super_company_id = f.super_company_id AND f.active = TRUE WHERE a.status != 'Pre-Entry' AND a.company_id = ?1 AND a.super_company_id = ?2 AND a.status NOT IN ('Sale', 'Scrap', 'Stolen') AND a.active = TRUE AND a.dismiss = FALSE", nativeQuery = true)
        List<Map<String, Object>> getAssetsForManagement(Integer companyId, Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT a.id, a.type, a.asset_tag_id, a.device_name,a.model, a.serial_no, a.manufacturer, a.supplier, a.status, c.project, c.project_location FROM asset a LEFT JOIN asset_project c ON a.id = c.asset_id AND a.company_id = c.company_id AND a.super_company_id = c.super_company_id AND c.status = TRUE WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND a.status IN ('Active', 'Project') AND a.active = TRUE AND a.dismiss = FALSE")
        List<Map<String, Object>> getAssetsForProjectManagement(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.id, a.asset_tag_id, a.device_name,a.type, a.model, a.serial_no, a.amc_status, a.insurance_status, a.warranty_status, a.finance_details, a.manufacturer, a.supplier, a.status, a.active FROM asset a WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND a.status = 'In-Transit' AND a.active = TRUE AND a.dismiss = FALSE ", nativeQuery = true)
        List<Map<String, Object>> geAssetForLogistics(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.id, a.asset_tag_id, a.device_name,a.type, a.model, a.serial_no, a.manufacturer, a.supplier, a.status, a.finance_details FROM asset a WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND a.status NOT IN ('Sale', 'Scrap', 'Stolen','Pre-Entry','In-Transit') AND a.active = TRUE AND a.dismiss = FALSE ORDER BY a.finance_details ASC", nativeQuery = true)
        List<Map<String, Object>> getAssetsForFinance(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT cost, invoice_date FROM asset WHERE id = ?1 AND company_id = ?2 AND super_company_id = ?3", nativeQuery = true)
        List<Map<String, Object>> getAssetCostAndPurchase(Long assetId, Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.id, a.asset_tag_id, a.device_name,a.type, a.model, a.serial_no, a.manufacturer, a.supplier FROM asset a LEFT JOIN asset_amc c ON a.id = c.asset_id AND a.company_id = c.company_id AND a.super_company_id = c.super_company_id WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND a.dismiss = FALSE AND a.active = TRUE AND a.status NOT IN ('Sale', 'Scrap', 'Stolen','Pre-Entry','In-Transit') AND (a.amc_status = FALSE OR DATEDIFF(c.end_date, CURDATE()) < 6) AND (a.warranty_status = FALSE OR DATEDIFF(a.warranty_end_date, CURDATE()) < 30)", nativeQuery = true)
        List<Map<String, Object>> getAssetsForAMC(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.id, a.asset_tag_id, a.device_name,a.type, a.model, a.serial_no, a.manufacturer, a.supplier FROM asset a LEFT JOIN asset_insurance c ON a.id = c.asset_id AND a.company_id = c.company_id AND a.super_company_id = c.super_company_id WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND a.active = TRUE AND a.status NOT IN ('Sale', 'Scrap', 'Stolen','Pre-Entry','In-Transit') AND (a.insurance_status = FALSE OR DATEDIFF(c.end_date, CURDATE()) < 6) AND a.dismiss = FALSE", nativeQuery = true)
        List<Map<String, Object>> getAssetsForInsurance(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.id, a.assetTagId, a.device_name,a.type, a.model, a.serialNo, a.manufacturer, a.status, a.active FROM asset a WHERE a.companyId = ?1 AND a.insurence_status AND a.superCompanyId = ?2 AND a.status NOT IN ('Sale', 'Scrap', 'Stolen','Pre-Entry','In-Transit');", nativeQuery = true)
        List<Map<String, Object>> findAssetsByInsurance(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT COUNT(id) as totalAsset, " +
                        "COUNT(CASE WHEN active = TRUE and status = 'Active' THEN 1 ELSE null END) as TotalActive, " +
                        "COUNT(CASE WHEN active = TRUE and status = 'Assigned' THEN 1 ELSE null END) as TotalAssign, " +
                        "COUNT(CASE WHEN active = TRUE and status = 'Deployed' THEN 1 ELSE null END) as TotalDeployment, "
                        +
                        "COUNT(CASE WHEN active = false and status = 'Scrap' THEN 1 ELSE null END) as TotalScrap, " +
                        "COUNT(CASE WHEN active = false and status = 'Sale' THEN 1 ELSE null END) as TotalSold, " +
                        "COUNT(CASE WHEN active = false and status = 'Pre-Entry' THEN 1 ELSE null END) as TotalPreEntry, "
                        +
                        "COUNT(CASE WHEN active = false and status = 'In-Transit' THEN 1 ELSE null END) as TotalInTransit "
                        +
                        "FROM asset WHERE company_id = ?1 AND super_company_id = ?2 AND dismiss = FALSE", nativeQuery = true)
        List<Map<String, Object>> getAssetMiniDetail(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.type, COUNT(CASE WHEN a.active = TRUE THEN a.id ELSE NULL END) AS Working, " +
                        "COUNT(CASE WHEN a.active = FALSE THEN a.id ELSE NULL END) AS Notworking " +
                        "FROM asset a where a.status != 'Pre-Entry' AND company_id=?1 and super_company_id=?2 AND dismiss = FALSE  GROUP BY a.type", nativeQuery = true)
        List<Map<String, Object>> getAssetTypeWish(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.status AS StatusLabel, " +
                        "COUNT(a.id) AS Working " +
                        "FROM asset a " +
                        "WHERE company_id = ?1 AND super_company_id = ?2 AND dismiss = FALSE "
                        +
                        "GROUP BY a.status ", nativeQuery = true)
        List<Map<String, Object>> getAssetStatusWish(Integer companyId, Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT department, SUM(Working) AS TotalWorking " +
                        "FROM (" +
                        "   SELECT reserve_dept AS department, COUNT(id) AS Working " +
                        "   FROM asset " +
                        "   WHERE reserve_dept IS NOT NULL " +
                        "       AND active = TRUE " +
                        "       AND company_id = ?1 " +
                        "       AND super_company_id = ?2 " +
                        "       AND dismiss = FALSE " +
                        "       AND status NOT IN ('Pre-Entry', 'In-Transit') " +
                        "   GROUP BY reserve_dept " +
                        "   UNION ALL " +
                        "   SELECT a.department, COUNT(b.id) AS Working " +
                        "   FROM asset_assign_history a " +
                        "   JOIN asset b ON a.asset_id = b.id " +
                        "       AND a.company_id = b.company_id " +
                        "       AND a.super_company_id = b.super_company_id " +
                        "       AND a.status = TRUE " +
                        "       AND b.dismiss = FALSE " +
                        "   WHERE b.active = TRUE " +
                        "       AND b.company_id = ?1 " +
                        "       AND b.super_company_id = ?2 " +
                        "       AND b.status NOT IN ('Pre-Entry', 'In-Transit') " +
                        "   GROUP BY a.department " +
                        ") AS combined_data " +
                        "GROUP BY department")
        List<Map<String, Object>> getAssetDepartmentWish(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT type, " +
                        "COUNT(id) AS asset, " +
                        "COUNT(CASE WHEN status = 'Assigned' THEN id ELSE NULL END) AS assign, " +
                        "COUNT(CASE WHEN status = 'Deployed' THEN id ELSE NULL END) AS deployd " +
                        "FROM asset " +
                        "WHERE active = TRUE AND status NOT IN ('Pre-Entry', 'In-Transit') AND company_id = ?1 AND super_company_id = ?2 AND dismiss = FALSE "
                        +
                        "GROUP BY type", nativeQuery = true)
        List<Map<String, Object>> getAssetReportTypeWish(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT type, COUNT(id) AS total,COALESCE(SUM(COALESCE(sale_value, 0)), 0) AS amount FROM asset where status='Sale' AND company_id=?1 AND super_company_id=?2 AND dismiss = FALSE  GROUP BY type", nativeQuery = true)
        List<Map<String, Object>> getAssetReportForSale(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.type, a.asset_tag_id, a.serial_no, a.model, a.sale_date, a.sale_value, b.name, b.employee_id "
                        +
                        "FROM asset a " +
                        "LEFT JOIN employees b ON (a.sold_person = b.employee_id " +
                        "AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) " +
                        "WHERE a.sale_status = true AND a.type = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND a.dismiss = FALSE AND a.status NOT IN ('Pre-Entry', 'In-Transit')", nativeQuery = true)
        List<Map<String, Object>> getAssetReportForSaleDetail(String type, Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT location, " +
                        "COUNT(id) AS asset, " +
                        "COUNT(CASE WHEN status = 'Assigned' THEN id ELSE NULL END) AS assign, " +
                        "COUNT(CASE WHEN status = 'Deployed' THEN id ELSE NULL END) AS deployd " +
                        "FROM asset " +
                        "WHERE active = TRUE AND status NOT IN ('Pre-Entry', 'In-Transit') AND company_id = ?1 AND super_company_id = ?2 AND dismiss = FALSE  AND location IS NOT NULL  "
                        +
                        "GROUP BY location", nativeQuery = true)
        List<Map<String, Object>> getAssetReportLocationWish(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT " +
                        "    b.name, c.asset_category_id AS catId, " +
                        "    COUNT(a.id) AS asset, " +
                        "    COUNT(CASE WHEN a.status = 'Assigned' THEN a.id ELSE NULL END) AS assign, " +
                        "    COUNT(CASE WHEN a.status = 'Deployed' THEN a.id ELSE NULL END) AS deployd " +
                        "FROM " +
                        "    asset a " +
                        "JOIN " +
                        "    asset_details c ON (a.id = c.asset_id " +
                        "        AND a.company_id = c.company_id " +
                        "        AND a.super_company_id = c.super_company_id) " +
                        "JOIN " +
                        "    asset_category b ON (c.asset_category_id = b.id " +
                        "        AND a.company_id = b.company_id " +
                        "        AND a.super_company_id = b.super_company_id) " +
                        "WHERE " +
                        "    a.active = TRUE AND a.dismiss = FALSE AND a.finance_details = true "
                        +
                        "        AND a.company_id = ?1 " +
                        "        AND a.super_company_id = ?2  AND a.status NOT IN ('Pre-Entry', 'In-Transit')" +
                        "GROUP BY c.asset_category_id, b.name", nativeQuery = true)
        List<Map<String, Object>> getAssetCategoryWish(Integer companyId, Integer superCompanyId);

        // @Query(value = "SELECT
        // asset_tag_id,data01,data02,data03,data04,data05,data06,data07,data08,data09,data10,data11,data12,data13,data14,data15,data16,
        // serial_no, model, status, NULL as employee, NULL as location "
        // +
        // "FROM asset " +
        // "WHERE type = ?1 AND company_id = ?2 AND super_company_id = ?3 AND
        // logistics_status = TRUE AND dismiss = FALSE AND active = TRUE AND status NOT
        // IN ('Assigned', 'Deployed') "
        // +

        // "UNION ALL " +

        // "SELECT
        // a.asset_tag_id,a.data01,a.data02,a.data03,a.data04,a.data05,a.data06,a.data07,a.data08,a.data09,a.data10,a.data11,a.data12,a.data13,a.data14,a.data15,a.data16,a.serial_no,
        // a.model, a.status, aa.employee, NULL as location "
        // +
        // "FROM asset a " +
        // "LEFT JOIN asset_assign_history aa ON (a.id = aa.asset_id AND a.company_id =
        // aa.company_id AND a.super_company_id = aa.super_company_id AND aa.status =
        // TRUE) "
        // +
        // "WHERE a.type = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND
        // a.logistics_status = TRUE AND a.dismiss = FALSE AND a.active = TRUE AND
        // a.status = 'Assigned' "
        // +

        // "UNION ALL " +

        // "SELECT
        // a.asset_tag_id,a.data01,a.data02,a.data03,a.data04,a.data05,a.data06,a.data07,a.data08,a.data09,a.data10,a.data11,a.data12,a.data13,a.data14,a.data15,a.data16,a.serial_no,
        // a.model, a.status, NULL as employee, aa.location_name as location "
        // +
        // "FROM asset a " +
        // "LEFT JOIN asset_deployment aa ON (a.id = aa.asset_id AND a.company_id =
        // aa.company_id AND a.super_company_id = aa.super_company_id AND aa.status =
        // TRUE) "
        // +
        // "WHERE a.type = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND
        // a.logistics_status = TRUE AND a.dismiss = FALSE AND a.active = TRUE AND
        // a.status = 'Deployed' ", nativeQuery = true)
        // List<Map<String, Object>> getAssetReportDetailsTypeWish(String name, Integer
        // companyId,
        // Integer superCompanyId);

        @Query(value = "SELECT " +
                        "a.asset_tag_id, " +
                        "a.data01, a.data02, a.data03, a.data04, a.data05, a.data06, a.data07, a.data08, " +
                        "a.data09, a.data10, a.data11, a.data12, a.data13, a.data14, a.data15, a.data16, " +
                        "a.serial_no, a.model, a.manufacturer, a.status, " +
                        "CASE WHEN a.status = 'Assigned' THEN aa.employee ELSE NULL END AS employee, " +
                        "CASE WHEN a.status = 'Deployed' THEN ad.location_name ELSE NULL END AS location " +
                        "FROM asset a " +
                        "LEFT JOIN asset_assign_history aa " +
                        "ON a.id = aa.asset_id " +
                        "AND a.company_id = aa.company_id " +
                        "AND a.super_company_id = aa.super_company_id " +
                        "AND aa.status = TRUE " +
                        "LEFT JOIN asset_deployment ad " +
                        "ON a.id = ad.asset_id " +
                        "AND a.company_id = ad.company_id " +
                        "AND a.super_company_id = ad.super_company_id " +
                        "AND ad.status = TRUE " +
                        "WHERE a.type = ?1 AND a.status NOT IN ('Pre-Entry', 'In-Transit')" +
                        "AND a.company_id = ?2 " +
                        "AND a.super_company_id = ?3 " +
                        "AND a.dismiss = FALSE " +
                        "AND a.active = TRUE", nativeQuery = true)
        List<Map<String, Object>> getAssetReportDetailsTypeWish(String type, Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT asset_tag_id, serial_no, model, status, NULL as employee, NULL as location "
                        +
                        "FROM asset " +
                        "WHERE location = ?1 AND company_id = ?2 AND super_company_id = ?3 AND active = TRUE AND dismiss = FALSE  AND status NOT IN ('Assigned', 'Deployed','Pre-Entry','In-Transit') "
                        +

                        "UNION ALL " +

                        "SELECT a.asset_tag_id, a.serial_no, a.model, a.status, aa.employee, NULL as location "
                        +
                        "FROM asset a " +
                        "LEFT JOIN asset_assign_history aa ON (a.id = aa.asset_id AND a.company_id = aa.company_id AND a.super_company_id = aa.super_company_id AND aa.status = TRUE) "
                        +
                        "WHERE a.location = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND a.dismiss = FALSE AND a.active = TRUE AND a.status = 'Assigned' "
                        +

                        "UNION ALL " +

                        "SELECT a.asset_tag_id, a.serial_no, a.model, a.status, NULL as employee, aa.location_name as location "
                        +
                        "FROM asset a " +
                        "LEFT JOIN asset_deployment aa ON (a.id = aa.asset_id AND a.company_id = aa.company_id AND a.super_company_id = aa.super_company_id AND aa.status = TRUE) "
                        +
                        "WHERE a.location = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND a.dismiss = FALSE AND a.active = TRUE AND a.status = 'Deployed'", nativeQuery = true)
        List<Map<String, Object>> getAssetReportDetailsLocationWish(String name, Integer companyId,
                        Integer superCompanyId);

        @Query(value = "SELECT a.asset_tag_id, a.serial_no, a.model, a.status, NULL as employee, NULL as location " +
                        "FROM asset a " +
                        "LEFT JOIN asset_details b ON (a.id = b.asset_id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
                        +
                        "WHERE b.asset_category_id = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3  AND a.dismiss = FALSE AND a.finance_details = true AND a.active = TRUE AND a.status NOT IN ('Assigned', 'Deployed','Pre-Entry','In-Transit') "
                        +
                        "UNION ALL " +
                        "SELECT a.asset_tag_id, a.serial_no, a.model, a.status, aa.employee, NULL as location " +
                        "FROM asset a " +
                        "LEFT JOIN asset_details b ON (a.id = b.asset_id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
                        +
                        "LEFT JOIN asset_assign_history aa ON (a.id = aa.asset_id AND a.company_id = aa.company_id AND a.super_company_id = aa.super_company_id AND aa.status = TRUE) "
                        +
                        "WHERE b.asset_category_id = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND a.dismiss = FALSE AND a.finance_details = true AND a.active = TRUE AND a.status = 'Assigned' "
                        +
                        "UNION ALL " +
                        "SELECT a.asset_tag_id, a.serial_no, a.model, a.status, NULL as employee, aa.location_name as location "
                        +
                        "FROM asset a " +
                        "LEFT JOIN asset_details b ON (a.id = b.asset_id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
                        +
                        "LEFT JOIN asset_deployment aa ON (a.id = aa.asset_id AND a.company_id = aa.company_id AND a.super_company_id = aa.super_company_id AND aa.status = TRUE) "
                        +
                        "WHERE b.asset_category_id = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND a.dismiss = FALSE AND a.finance_details = true AND a.active = TRUE AND a.status = 'Deployed'", nativeQuery = true)
        List<Map<String, Object>> getAssetReportDetailsCategoryWish(String catId, Integer companyId,
                        Integer superCompanyId);

        @Query(value = "SELECT a.type,b.status, a.location,a.sub_location, a.model, a.serial_no, a.asset_tag_id, a.manufacturer as name, "
                        +
                        "TRIM(TRAILING ',' FROM CONCAT_WS(',', " +
                        " IF(a.data01 IS NOT NULL, CONCAT_WS('-', IFNULL(d1.caption, ''), a.data01), NULL), " +
                        " IF(a.data02 IS NOT NULL, CONCAT_WS('-', IFNULL(d2.caption, ''), a.data02), NULL), " +
                        " IF(a.data03 IS NOT NULL, CONCAT_WS('-', IFNULL(d3.caption, ''), a.data03), NULL), " +
                        " IF(a.data04 IS NOT NULL, CONCAT_WS('-', IFNULL(d4.caption, ''), a.data04), NULL), " +
                        " IF(a.data05 IS NOT NULL, CONCAT_WS('-', IFNULL(d5.caption, ''), a.data05), NULL), " +
                        " IF(a.data06 IS NOT NULL, CONCAT_WS('-', IFNULL(d6.caption, ''), a.data06), NULL), " +
                        " IF(a.data07 IS NOT NULL, CONCAT_WS('-', IFNULL(d7.caption, ''), a.data07), NULL), " +
                        " IF(a.data08 IS NOT NULL, CONCAT_WS('-', IFNULL(d8.caption, ''), a.data08), NULL), " +
                        " IF(a.data09 IS NOT NULL, CONCAT_WS('-', IFNULL(d9.caption, ''), a.data09), NULL), " +
                        " IF(a.data10 IS NOT NULL, CONCAT_WS('-', IFNULL(d10.caption, ''), a.data10), NULL), " +
                        " IF(a.data11 IS NOT NULL, CONCAT_WS('-', IFNULL(d11.caption, ''), a.data11), NULL), " +
                        " IF(a.data12 IS NOT NULL, CONCAT_WS('-', IFNULL(d12.caption, ''), a.data12), NULL), " +
                        " IF(a.data13 IS NOT NULL, CONCAT_WS('-', IFNULL(d13.caption, ''), a.data13), NULL), " +
                        " IF(a.data14 IS NOT NULL, CONCAT_WS('-', IFNULL(d14.caption, ''), a.data14), NULL), " +
                        " IF(a.data15 IS NOT NULL, CONCAT_WS('-', IFNULL(d15.caption, ''), a.data15), NULL), " +
                        " IF(a.data16 IS NOT NULL, CONCAT_WS('-', IFNULL(d16.caption, ''), a.data16), NULL) " +
                        ")) AS description " +
                        "FROM asset a " +
                        "JOIN asset_assign_history b ON (a.id = b.asset_id) " +
                        "JOIN asset_type d ON (a.type = d.type AND d.company_id = a.company_id AND d.super_company_id = a.super_company_id) "
                        +
                        "LEFT JOIN asset_type_detail d1 ON d.id = d1.detail_id AND d1.model = 'data01' " +
                        "AND d.company_id = d1.company_id AND d.super_company_id = d1.super_company_id " +
                        "LEFT JOIN asset_type_detail d2 ON d.id = d2.detail_id AND d2.model = 'data02' " +
                        "AND d.company_id = d2.company_id AND d.super_company_id = d2.super_company_id " +
                        "LEFT JOIN asset_type_detail d3 ON d.id = d3.detail_id AND d3.model = 'data03' " +
                        "AND d.company_id = d3.company_id AND d.super_company_id = d3.super_company_id " +
                        "LEFT JOIN asset_type_detail d4 ON d.id = d4.detail_id AND d4.model = 'data04' " +
                        "AND d.company_id = d4.company_id AND d.super_company_id = d4.super_company_id " +
                        "LEFT JOIN asset_type_detail d5 ON d.id = d5.detail_id AND d5.model = 'data05' " +
                        "AND d.company_id = d5.company_id AND d.super_company_id = d5.super_company_id " +
                        "LEFT JOIN asset_type_detail d6 ON d.id = d6.detail_id AND d6.model = 'data06' " +
                        "AND d.company_id = d6.company_id AND d.super_company_id = d6.super_company_id " +
                        "LEFT JOIN asset_type_detail d7 ON d.id = d7.detail_id AND d7.model = 'data07' " +
                        "AND d.company_id = d7.company_id AND d.super_company_id = d7.super_company_id " +
                        "LEFT JOIN asset_type_detail d8 ON d.id = d8.detail_id AND d8.model = 'data08' " +
                        "AND d.company_id = d8.company_id AND d.super_company_id = d8.super_company_id " +
                        "LEFT JOIN asset_type_detail d9 ON d.id = d9.detail_id AND d9.model = 'data09' " +
                        "AND d.company_id = d9.company_id AND d.super_company_id = d9.super_company_id " +
                        "LEFT JOIN asset_type_detail d10 ON d.id = d10.detail_id AND d10.model = 'data10' " +
                        "AND d.company_id = d10.company_id AND d.super_company_id = d10.super_company_id " +
                        "LEFT JOIN asset_type_detail d11 ON d.id = d11.detail_id AND d11.model = 'data11' " +
                        "AND d.company_id = d11.company_id AND d.super_company_id = d11.super_company_id " +
                        "LEFT JOIN asset_type_detail d12 ON d.id = d12.detail_id AND d12.model = 'data12' " +
                        "AND d.company_id = d12.company_id AND d.super_company_id = d12.super_company_id " +
                        "LEFT JOIN asset_type_detail d13 ON d.id = d13.detail_id AND d13.model = 'data13' " +
                        "AND d.company_id = d13.company_id AND d.super_company_id = d13.super_company_id " +
                        "LEFT JOIN asset_type_detail d14 ON d.id = d14.detail_id AND d14.model = 'data14' " +
                        "AND d.company_id = d14.company_id AND d.super_company_id = d14.super_company_id " +
                        "LEFT JOIN asset_type_detail d15 ON d.id = d15.detail_id AND d15.model = 'data15' " +
                        "AND d.company_id = d15.company_id AND d.super_company_id = d15.super_company_id " +
                        "LEFT JOIN asset_type_detail d16 ON d.id = d16.detail_id AND d16.model = 'data16' " +
                        "AND d.company_id = d16.company_id AND d.super_company_id = d16.super_company_id " +
                        "WHERE b.employee_code = ?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND a.dismiss = FALSE ", nativeQuery = true)
        List<Map<String, Object>> getAssetReportDetailsEmployeeWish(String empCode, Integer companyId,
                        Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT distinct year_code FROM asset_depreciation WHERE company_id=?1 AND super_company_id=?2 ")
        List<Map<String, Object>> yearCodeControl(Integer companyId, Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT a.id, a.type, a.asset_tag_id, a.model, a.serial_no, a.manufacturer, a.supplier, a.status, c.project,c.project_location,c.start_date,c.end_date,c.note "
                        +
                        "FROM asset a " +
                        "LEFT JOIN asset_project c ON a.id = c.asset_id AND a.company_id = c.company_id AND a.super_company_id = c.super_company_id AND c.status = TRUE "
                        +
                        "WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND a.status IN ('Project') AND a.active = TRUE AND a.dismiss = FALSE")
        List<Map<String, Object>> getAssetForProject(Integer companyId, Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT a.id, a.type, a.asset_tag_id, a.model, a.serial_no, " +
                        "c.project, c.project_location, c.start_date, c.end_date, c.note " +
                        "FROM asset a " +
                        "LEFT JOIN asset_project c ON a.id = c.asset_id " +
                        "AND a.company_id = c.company_id " +
                        "AND a.super_company_id = c.super_company_id " +
                        "AND c.status = TRUE " +
                        "WHERE a.company_id = ?1 " +
                        "AND a.super_company_id = ?2 " +
                        "AND a.status IN ('Project') " +
                        "AND a.active = TRUE " +
                        "AND a.dismiss = FALSE " +
                        "AND DATEDIFF(c.end_date, CURRENT_DATE) < 10")
        List<Map<String, Object>> getAssetForProjectForPopUp(Integer companyId, Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT b.year_code, c.name AS cat, d.name AS subcat, SUM(depreciation) AS amount "
                        +
                        "FROM asset a " +
                        "JOIN asset_details e ON (a.id = e.asset_id) " +
                        "JOIN asset_depreciation b ON (a.id = b.asset_id) " +
                        "JOIN asset_category c ON (e.asset_category_id = c.id) " +
                        "JOIN asset_sub_category d ON (e.asset_sub_category_id = d.id) " +
                        "WHERE a.finance_details = TRUE " +
                        "AND e.put_to_use_date_availability = TRUE " +
                        "AND a.company_id = ?1 " +
                        "AND a.super_company_id = ?2 " +
                        "AND b.year_code = ?3 AND a.dismiss = FALSE " +
                        "GROUP BY e.asset_category_id, e.asset_sub_category_id")
        List<Map<String, Object>> reportOnDepreciation(int companyId, int superCompanyId, String yearCode);

        @Query(value = "SELECT COUNT(id) as totalAsset, " +
                        "COUNT(CASE WHEN active = TRUE and status = 'Active' THEN 1 ELSE null END) as TotalActive, " +
                        "COUNT(CASE WHEN active = TRUE and status = 'Assigned' THEN 1 ELSE null END) as TotalAssign, " +
                        "COUNT(CASE WHEN active = TRUE and status = 'Deployed' THEN 1 ELSE null END) as TotalDeployment, "
                        +
                        "COUNT(CASE WHEN active = false and status = 'Scrap' THEN 1 ELSE null END) as TotalScrap, " +
                        "COUNT(CASE WHEN active = false and status = 'Sale' THEN 1 ELSE null END) as TotalSold " +
                        "FROM asset WHERE super_company_id = ?1 AND status NOT IN ('Pre-Entry', 'In-Transit')  AND dismiss = FALSE ", nativeQuery = true)
        List<Map<String, Object>> getAssetMiniDetailForSuperadmin(Integer superCompanyId);

        @Query(value = "SELECT a.type, COUNT(CASE WHEN a.active = TRUE THEN a.id ELSE NULL END) AS Working, " +
                        "COUNT(CASE WHEN a.active = FALSE THEN a.id ELSE NULL END) AS Notworking " +
                        "FROM asset a where super_company_id=?1  AND dismiss = FALSE AND a.status NOT IN ('Pre-Entry', 'In-Transit')  GROUP BY a.type", nativeQuery = true)
        List<Map<String, Object>> getAssetTypeWishForSuperadmin(Integer superCompanyId);

        @Query(value = "SELECT a.status AS StatusLabel, " +
                        "COUNT(a.id) AS Working " +
                        "FROM asset a " +
                        "WHERE a.super_company_id = ?1  AND a.dismiss = FALSE AND a.status NOT IN ('Pre-Entry', 'In-Transit')  "
                        +
                        "GROUP BY a.status ", nativeQuery = true)
        List<Map<String, Object>> getAssetStatusWishForSuperAdmin(Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT department, SUM(Working) AS TotalWorking " +
                        "FROM (" +
                        "   SELECT reserve_dept AS department, COUNT(id) AS Working " +
                        "   FROM asset " +
                        "   WHERE reserve_dept IS NOT NULL " +
                        "       AND active = TRUE " +
                        "       AND super_company_id = ?1 AND dismiss = FALSE  AND status NOT IN ('Pre-Entry', 'In-Transit')"
                        +
                        "   GROUP BY reserve_dept " +
                        "   UNION ALL " +
                        "   SELECT a.department, COUNT(b.id) AS Working " +
                        "   FROM asset_assign_history a " +
                        "       JOIN asset b ON (a.asset_id = b.id " +
                        "           AND a.company_id = b.company_id " +
                        "           AND a.super_company_id = b.super_company_id " +
                        "           AND a.status = TRUE) " +
                        "   WHERE b.active = TRUE " +
                        "       AND b.super_company_id = ?1 AND b.dismiss = FALSE AND b.status NOT IN ('Pre-Entry', 'In-Transit')"
                        +
                        "   GROUP BY a.department " +
                        ") AS combined_data " +
                        "GROUP BY department")
        List<Map<String, Object>> getAssetDepartmentWishForSuperadmin(Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT b.type as assetType, " +
                        "       b.asset_tag_id, " +
                        "       DATEDIFF(a.end_date, :givenDate) AS days_left, " +
                        "       'Insurance' as type " +
                        "FROM asset_insurance a " +
                        "JOIN asset b ON (a.asset_id = b.id " +
                        "                  AND a.company_id = b.company_id " +
                        "                  AND a.super_company_id = b.super_company_id) " +
                        "WHERE DATEDIFF(a.end_date, :givenDate) < 20 AND DATEDIFF(a.end_date, :givenDate) > 0 " +
                        "      AND a.company_id = :companyId " +
                        "      AND a.super_company_id = :superCompanyId  AND b.dismiss = FALSE "
                        +

                        "UNION " +

                        "SELECT b.type as assetType, " +
                        "       b.asset_tag_id, " +
                        "       DATEDIFF(a.end_date, :givenDate) AS days_left, " +
                        "       'AMC' as type " +
                        "FROM asset_amc a " +
                        "JOIN asset b ON (a.asset_id = b.id " +
                        "                  AND a.company_id = b.company_id " +
                        "                  AND a.super_company_id = b.super_company_id) " +
                        "WHERE DATEDIFF(a.end_date, :givenDate) < 20 AND DATEDIFF(a.end_date, :givenDate) > 0 " +
                        "      AND a.company_id = :companyId " +
                        "      AND a.super_company_id = :superCompanyId  AND b.dismiss = FALSE "
                        +

                        "UNION " +

                        "SELECT a.type, " +
                        "       a.asset_tag_id, " +
                        "       DATEDIFF(a.warranty_end_date, :givenDate) AS days_left, " +
                        "       'Warranty' as type " +
                        "FROM asset a " +
                        "WHERE DATEDIFF(a.warranty_end_date, :givenDate) < 20 AND DATEDIFF(a.warranty_end_date, :givenDate) > 0 "
                        +
                        "      AND a.company_id = :companyId " +
                        "      AND a.super_company_id = :superCompanyId  AND a.dismiss = FALSE")
        List<Map<String, Object>> getNotification(@Param("givenDate") String givenDate,
                        @Param("companyId") int companyId,
                        @Param("superCompanyId") int superCompanyId);

        @Query(value = "SELECT " +
                        "a.type, " +
                        "a.location, " +
                        "a.sub_location, " +
                        "b.employee, " +
                        "b.employee_code, " +
                        "a.model, " +
                        "a.serial_no, " +
                        "a.asset_tag_id, " +
                        "a.manufacturer, " +
                        "a.supplier, " +
                        "a.warranty_status, " +
                        "a.amc_status, " +
                        "a.insurance_status, " +
                        "a.status, " +
                        "a.cost, " +
                        "a.po_number, " +
                        "a.po_date, " +
                        "a.invoice_number, " +
                        "a.invoice_date, " +
                        "a.warranty_start_date, " +
                        "a.warranty_end_date, " +
                        "e1.start_date as amc_start_date, " +
                        "e1.end_date as amc_end_date, " +
                        "e2.start_date as insurance_start_date, " +
                        "e2.end_date as insurance_end_date, " +
                        "TRIM(TRAILING ',' FROM CONCAT_WS(',', " +
                        " IF(a.data01 IS NOT NULL, CONCAT_WS('-', IFNULL(d1.caption, ''), a.data01), NULL), " +
                        " IF(a.data02 IS NOT NULL, CONCAT_WS('-', IFNULL(d2.caption, ''), a.data02), NULL), " +
                        " IF(a.data03 IS NOT NULL, CONCAT_WS('-', IFNULL(d3.caption, ''), a.data03), NULL), " +
                        " IF(a.data04 IS NOT NULL, CONCAT_WS('-', IFNULL(d4.caption, ''), a.data04), NULL), " +
                        " IF(a.data05 IS NOT NULL, CONCAT_WS('-', IFNULL(d5.caption, ''), a.data05), NULL), " +
                        " IF(a.data06 IS NOT NULL, CONCAT_WS('-', IFNULL(d6.caption, ''), a.data06), NULL), " +
                        " IF(a.data07 IS NOT NULL, CONCAT_WS('-', IFNULL(d7.caption, ''), a.data07), NULL), " +
                        " IF(a.data08 IS NOT NULL, CONCAT_WS('-', IFNULL(d8.caption, ''), a.data08), NULL), " +
                        " IF(a.data09 IS NOT NULL, CONCAT_WS('-', IFNULL(d9.caption, ''), a.data09), NULL), " +
                        " IF(a.data10 IS NOT NULL, CONCAT_WS('-', IFNULL(d10.caption, ''), a.data10), NULL), " +
                        " IF(a.data11 IS NOT NULL, CONCAT_WS('-', IFNULL(d11.caption, ''), a.data11), NULL), " +
                        " IF(a.data12 IS NOT NULL, CONCAT_WS('-', IFNULL(d12.caption, ''), a.data12), NULL), " +
                        " IF(a.data13 IS NOT NULL, CONCAT_WS('-', IFNULL(d13.caption, ''), a.data13), NULL), " +
                        " IF(a.data14 IS NOT NULL, CONCAT_WS('-', IFNULL(d14.caption, ''), a.data14), NULL), " +
                        " IF(a.data15 IS NOT NULL, CONCAT_WS('-', IFNULL(d15.caption, ''), a.data15), NULL), " +
                        " IF(a.data16 IS NOT NULL, CONCAT_WS('-', IFNULL(d16.caption, ''), a.data16), NULL) " +
                        ")) AS description " +
                        "FROM asset a " +
                        "LEFT JOIN asset_assign_history b ON a.id = b.asset_id AND b.status = TRUE " +
                        "LEFT JOIN asset_amc e1 ON a.id = e1.asset_id AND e1.company_id = a.company_id AND e1.super_company_id = a.super_company_id "
                        +
                        "LEFT JOIN asset_insurance e2 ON a.id = e2.asset_id AND e2.company_id = a.company_id AND e2.super_company_id = a.super_company_id "
                        +
                        "LEFT JOIN asset_type d ON a.type = d.type AND d.company_id = a.company_id AND d.super_company_id = a.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d1 ON d.id = d1.detail_id AND d1.model = 'data01' AND d.company_id = d1.company_id AND d.super_company_id = d1.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d2 ON d.id = d2.detail_id AND d2.model = 'data02' AND d.company_id = d2.company_id AND d.super_company_id = d2.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d3 ON d.id = d3.detail_id AND d3.model = 'data03' AND d.company_id = d3.company_id AND d.super_company_id = d3.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d4 ON d.id = d4.detail_id AND d4.model = 'data04' AND d.company_id = d4.company_id AND d.super_company_id = d4.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d5 ON d.id = d5.detail_id AND d5.model = 'data05' AND d.company_id = d5.company_id AND d.super_company_id = d5.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d6 ON d.id = d6.detail_id AND d6.model = 'data06' AND d.company_id = d6.company_id AND d.super_company_id = d6.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d7 ON d.id = d7.detail_id AND d7.model = 'data07' AND d.company_id = d7.company_id AND d.super_company_id = d7.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d8 ON d.id = d8.detail_id AND d8.model = 'data08' AND d.company_id = d8.company_id AND d.super_company_id = d8.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d9 ON d.id = d9.detail_id AND d9.model = 'data09' AND d.company_id = d9.company_id AND d.super_company_id = d9.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d10 ON d.id = d10.detail_id AND d10.model = 'data10' AND d.company_id = d10.company_id AND d.super_company_id = d10.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d11 ON d.id = d11.detail_id AND d11.model = 'data11' AND d.company_id = d11.company_id AND d.super_company_id = d11.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d12 ON d.id = d12.detail_id AND d12.model = 'data12' AND d.company_id = d12.company_id AND d.super_company_id = d12.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d13 ON d.id = d13.detail_id AND d13.model = 'data13' AND d.company_id = d13.company_id AND d.super_company_id = d13.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d14 ON d.id = d14.detail_id AND d14.model = 'data14' AND d.company_id = d14.company_id AND d.super_company_id = d14.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d15 ON d.id = d15.detail_id AND d15.model = 'data15' AND d.company_id = d15.company_id AND d.super_company_id = d15.super_company_id "
                        +
                        "LEFT JOIN asset_type_detail d16 ON d.id = d16.detail_id AND d16.model = 'data16' AND d.company_id = d16.company_id AND d.super_company_id = d16.super_company_id "
                        +
                        "WHERE " +
                        "a.company_id = ?1 " +
                        "AND a.super_company_id = ?2 " +
                        "AND a.dismiss = FALSE AND a.status NOT IN ('Pre-Entry', 'In-Transit')", nativeQuery = true)
        List<Map<String, Object>> getDetailAsset(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT count(id) FROM asset_amc WHERE company_id = ?1 AND super_company_id = ?2 AND status = TRUE AND DATEDIFF(end_date, CURRENT_DATE) <= 10 AND DATEDIFF(end_date, CURRENT_DATE) >= 0", nativeQuery = true)
        Integer getAMCCountNotification(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT count(id) FROM asset WHERE company_id = ?1 AND super_company_id = ?2  AND active = TRUE AND DATEDIFF(warranty_end_date, CURRENT_DATE) <= 10 AND DATEDIFF(warranty_end_date, CURRENT_DATE) >= 0", nativeQuery = true)
        Integer getWarrantyCountNotification(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT count(id) FROM asset_project WHERE  company_id = ?1 AND super_company_id = ?2 AND status = TRUE AND DATEDIFF(end_date, CURRENT_DATE) < 10", nativeQuery = true)
        Integer getProjectCountNotification(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT count(id) FROM asset_rent WHERE company_id = ?1 AND super_company_id = ?2 AND status = TRUE AND DATEDIFF(end_date, CURRENT_DATE) < 10", nativeQuery = true)
        Integer getRentCountNotification(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT count(id) FROM asset_insurance WHERE company_id = ?1 AND super_company_id = ?2 AND status = TRUE AND DATEDIFF(end_date, CURRENT_DATE) <= 10 AND DATEDIFF(end_date, CURRENT_DATE) >= 0", nativeQuery = true)
        Integer getInsuranceCountNotification(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT COUNT(*) " +
                        "FROM asset a " +
                        "JOIN asset_version b ON a.id = b.asset_id " +
                        "WHERE a.active = TRUE " +
                        "AND a.company_id = :companyId " +
                        "AND a.super_company_id = :superCompanyId " +
                        "AND DATE(b.created_date) BETWEEN CURDATE() - INTERVAL 6 DAY AND CURDATE()", nativeQuery = true)
        Integer countVersionsLast7Days(Integer companyId, Integer superCompanyId);

        @Query(value = "SELECT a.uuid, a.asset_tag_id, a.serial_no, a.status, b.log, b.created_date " +
                        "FROM asset a " +
                        "JOIN asset_version b ON a.id = b.asset_id " +
                        "WHERE a.active = TRUE " +
                        "AND a.company_id = :companyId " +
                        "AND a.super_company_id = :superCompanyId " +
                        "AND DATE(b.created_date) BETWEEN CURDATE() - INTERVAL 6 DAY AND CURDATE()", nativeQuery = true)
        List<Map<String, Object>> findAssetsWithLogLast7Days(Integer companyId, Integer superCompanyId);

}
