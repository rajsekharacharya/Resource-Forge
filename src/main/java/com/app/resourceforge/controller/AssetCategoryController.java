package com.app.resourceforge.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.model.AssetCategory;
import com.app.resourceforge.service.AssetCategoryService;

@RestController
@RequestMapping("/assetCategory")
public class AssetCategoryController {

    private final AssetCategoryService assetCategoryService;

    public AssetCategoryController(AssetCategoryService assetCategoryService) {
        this.assetCategoryService = assetCategoryService;
    }

    @GetMapping(value = "/getAllAssetCategory")
    public ResponseEntity<?> getAllAssetCategory() {
        return assetCategoryService.getAllAssetCategory();
    }

    @GetMapping(value = "/getAssetCategorys")
    public ResponseEntity<?> getAssetCategorys() {
        return assetCategoryService.getAssetCategorys();
    }

    @GetMapping(value = "/getAssetCategoryById")
    public ResponseEntity<?> getAssetCategoryById(@RequestParam Long id) {
        return assetCategoryService.getAssetCategoryById(id);
    }

    @PostMapping(value = "/createAssetCategory")
    public ResponseEntity<?> createAssetCategory(@RequestBody @Valid AssetCategory assetCategory) {
        return assetCategoryService.createAssetCategory(assetCategory);
    }

    @PutMapping(value = "/updateAssetCategory")
    public ResponseEntity<?> updateAssetCategory(@RequestBody @Valid AssetCategory assetCategory) {
        return assetCategoryService.updateAssetCategory(assetCategory);
    }

    @DeleteMapping(value = "/toggleAssetCategoryStatus")
    public ResponseEntity<?> toggleAssetCategoryStatus(@RequestParam Long id) {
        return assetCategoryService.toggleAssetCategoryStatus(id);
    }

}
