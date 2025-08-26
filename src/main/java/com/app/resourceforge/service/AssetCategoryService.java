package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.AssetCategory;

public interface AssetCategoryService {

    ResponseEntity<?> getAllAssetCategory();

    ResponseEntity<?> getAssetCategorys();

    ResponseEntity<?> getAssetCategoryById(Long id);

    ResponseEntity<?> createAssetCategory(@Valid AssetCategory assetCategory);

    ResponseEntity<?> updateAssetCategory(@Valid AssetCategory assetCategory);

    ResponseEntity<?> toggleAssetCategoryStatus(Long id);
    
}
