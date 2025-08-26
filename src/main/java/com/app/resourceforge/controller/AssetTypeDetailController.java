package com.app.resourceforge.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.AssetTypeDetail;
import com.app.resourceforge.service.AssetTypeDetailService;

@RestController
@RequestMapping("/assetTypeDetail")
public class AssetTypeDetailController {

    @Autowired
    AssetTypeDetailService assetTypeDetailService;

        @GetMapping(value = "/getAllAssetTypeDetail")
    public ResponseEntity<?> getAllAssetTypeDetail() {
        return assetTypeDetailService.getAllAssetTypeDetail();
    }

    @GetMapping(value = "/getAssetTypeDetails")
    public ResponseEntity<?> getAssetTypeDetails() {
        return assetTypeDetailService.getAssetTypeDetails();
    }

    @GetMapping(value = "/getAssetTypeDetailById")
    public ResponseEntity<?> getAssetTypeDetailById(@RequestParam Long id) {
        return assetTypeDetailService.getAssetTypeDetailById(id);
    }

    @PostMapping(value = "/createAssetTypeDetail")
    public ResponseEntity<?> createAssetTypeDetail(@RequestBody @Valid List<AssetTypeDetail> assetTypeDetail) {
        return assetTypeDetailService.createAssetTypeDetail(assetTypeDetail);
    }

    @PutMapping(value = "/updateAssetTypeDetail")
    public ResponseEntity<?> updateAssetTypeDetail(@RequestBody @Valid AssetTypeDetail assetTypeDetail) {
        return assetTypeDetailService.updateAssetTypeDetail(assetTypeDetail);
    }

    @DeleteMapping(value = "/toggleAssetTypeDetailStatus")
    public ResponseEntity<?> toggleAssetTypeDetailStatus(@RequestParam Long id) {
        return assetTypeDetailService.toggleAssetTypeDetailStatus(id);
    }
    
}
