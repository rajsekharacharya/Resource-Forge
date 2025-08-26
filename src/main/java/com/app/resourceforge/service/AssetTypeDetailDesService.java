package com.app.resourceforge.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.model.AssetTypeDetailDes;

public interface AssetTypeDetailDesService {

    ResponseEntity<?> getAllAssetTypeDetailDes();

    ResponseEntity<?> getAssetTypeDetailDess();

    ResponseEntity<?> getAssetTypeDetailDesById(Long id);

    ResponseEntity<?> createAssetTypeDetailDes(@Valid List<AssetTypeDetailDes> assetTypeDetailDes);

    ResponseEntity<?> updateAssetTypeDetailDes(@Valid AssetTypeDetailDes assetTypeDetailDes);

    ResponseEntity<?> toggleAssetTypeDetailDesStatus(Long id);
    
}
