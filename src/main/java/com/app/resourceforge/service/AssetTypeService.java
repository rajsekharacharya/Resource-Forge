package com.app.resourceforge.service;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.AssetType;

public interface AssetTypeService {

    ResponseEntity<?> getAllAssetType();

    ResponseEntity<?> getTotalAssetType();

    ResponseEntity<?> getAssetTypeById(Long id);

    ResponseEntity<?> createAssetType(@Valid AssetType assetType);

    ResponseEntity<?> updateAssetType(@Valid AssetType assetType);

    ResponseEntity<?> deleteAssetType(Long id);

    ResponseEntity<?> getAssetTypeDropDown(String type);

}
