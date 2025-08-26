package com.app.resourceforge.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.AssetSubCategory;

public interface AssetSubCategoryService {

    ResponseEntity<?> getAllAssetSubCategory();

    ResponseEntity<?> getAssetSubCategorys();

    ResponseEntity<?> getAssetSubCategoryById(Long id);

    ResponseEntity<?> createAssetSubCategory(@Valid List<AssetSubCategory> assetSubCategory);

    ResponseEntity<?> updateAssetSubCategory(@Valid AssetSubCategory assetSubCategory);

    ResponseEntity<?> toggleAssetSubCategoryStatus(Long id);
    
}
