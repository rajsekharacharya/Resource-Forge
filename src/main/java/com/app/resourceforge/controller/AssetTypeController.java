package com.app.resourceforge.controller;

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

import com.app.resourceforge.model.AssetType;
import com.app.resourceforge.service.AssetTypeService;

@RestController
@RequestMapping("/assetType")
public class AssetTypeController {

    @Autowired
    private AssetTypeService assetTypeService;

    @GetMapping(value = "/getAllAssetType")
    public ResponseEntity<?> getAllAssetType() {
        return assetTypeService.getAllAssetType();
    }

    @GetMapping(value = "/getTotalAssetType")
    public ResponseEntity<?> getTotalAssetType() {
        return assetTypeService.getTotalAssetType();
    }

    @GetMapping(value = "/getAssetTypeById")
    public ResponseEntity<?> getAssetTypeById(@RequestParam Long id) {
        return assetTypeService.getAssetTypeById(id);
    }

    @PostMapping(value = "/createAssetType", consumes = "application/json")
    public ResponseEntity<?> createAssetType(@RequestBody @Valid AssetType assetType) {
        return assetTypeService.createAssetType(assetType);
    }

    @PutMapping(value = "/updateAssetType", consumes = "application/json")
    public ResponseEntity<?> updateAssetType(@RequestBody @Valid AssetType assetType) {
        return assetTypeService.updateAssetType(assetType);
    }

    @DeleteMapping(value = "/deleteAssetType")
    public ResponseEntity<?> deleteAssetType(@RequestParam Long id) {
        return assetTypeService.deleteAssetType(id);
    }

    @GetMapping(value = "/getAssetTypeDropDown")
    public ResponseEntity<?> getAssetTypeDropDown(@RequestParam String type) {
        return assetTypeService.getAssetTypeDropDown(type);
    }

}
