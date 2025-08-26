package com.app.resourceforge.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.AssetTypeDetail;

public interface AssetTypeDetailService {

    ResponseEntity<?> getAllAssetTypeDetail();

    ResponseEntity<?> getAssetTypeDetails();

    ResponseEntity<?> getAssetTypeDetailById(Long id);

    ResponseEntity<?> createAssetTypeDetail(@Valid List<AssetTypeDetail> assetTypeDetail);

    ResponseEntity<?> updateAssetTypeDetail(@Valid AssetTypeDetail assetTypeDetail);

    ResponseEntity<?> toggleAssetTypeDetailStatus(Long id);
    
}
