package com.app.resourceforge.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.resourceforge.model.AssetCategory;

public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {

    List<AssetCategory> findByStatusAndCompanyId(boolean status, Integer companyId);

    Optional<AssetCategory> findByIdAndSuperCompanyIdAndCompanyId(Long id, Integer superCompanyId, Integer companyId);

    List<AssetCategory> findByCompanyId(Integer companyId);

    boolean existsByName(String name);

    @Query(value = "SELECT a.name AS categoryName, a.id AS categoryId, " +
            "b.name AS subCategoryName, b.id AS subCategoryId, b.life " +
            "FROM asset_category a " +
            "JOIN asset_sub_category b ON (a.id = b.asset_cat_id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
            +
            "WHERE a.company_id = ?1 AND a.super_company_id = ?2", nativeQuery = true)
    List<Map<String, Object>> getCategory(Integer companyId, Integer superCompanyId);


    @Query(value = "SELECT b.life " +
                   "FROM asset_category a " +
                   "JOIN asset_sub_category b ON (a.id = b.asset_cat_id " +
                   "AND a.company_id = b.company_id " +
                   "AND a.super_company_id = b.super_company_id) " +
                   "WHERE a.company_id = ?1 " +
                   "AND a.super_company_id = ?2 " +
                   "AND a.id = ?3 " +
                   "AND b.id = ?4", nativeQuery = true)
    Optional<Integer> findLifeByCategoryAndSubCategory(Integer companyId, Integer superCompanyId,Long CatId,Long subCatId);

}
