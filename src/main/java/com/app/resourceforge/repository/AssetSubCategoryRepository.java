package com.app.resourceforge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.AssetSubCategory;

public interface AssetSubCategoryRepository extends JpaRepository<AssetSubCategory,Long> {


    List<AssetSubCategory> findByCompanyId(Integer companyId);

    List<AssetSubCategory> findByStatusAndCompanyId(boolean status, Integer companyId);

    boolean existsByCompanyIdAndNameAndAssetCategoryId(Integer companyId,String name ,Long assetCategoryId);


    
}
