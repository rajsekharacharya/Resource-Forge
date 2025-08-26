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

import com.app.resourceforge.model.AssetSubCategory;
import com.app.resourceforge.service.AssetSubCategoryService;

@RestController
@RequestMapping("/assetSubCategory")
public class AssetSubCategoryController {

    @Autowired
    private AssetSubCategoryService assetSubCategoryService;

    @GetMapping(value = "/getAllAssetSubCategory")
    public ResponseEntity<?> getAllAssetSubCategory() {
        return assetSubCategoryService.getAllAssetSubCategory();
    }

    @GetMapping(value = "/getAssetSubCategorys")
    public ResponseEntity<?> getAssetSubCategorys() {
        return assetSubCategoryService.getAssetSubCategorys();
    }

    @GetMapping(value = "/getAssetSubCategoryById")
    public ResponseEntity<?> getAssetSubCategoryById(@RequestParam Long id) {
        return assetSubCategoryService.getAssetSubCategoryById(id);
    }

    @PostMapping(value = "/createAssetSubCategory")
    public ResponseEntity<?> createAssetSubCategory(@RequestBody @Valid List<AssetSubCategory> assetSubCategory) {
        return assetSubCategoryService.createAssetSubCategory(assetSubCategory);
    }

    @PutMapping(value = "/updateAssetSubCategory")
    public ResponseEntity<?> updateAssetSubCategory(@RequestBody @Valid AssetSubCategory assetSubCategory) {
        return assetSubCategoryService.updateAssetSubCategory(assetSubCategory);
    }

    @DeleteMapping(value = "/toggleAssetSubCategoryStatus")
    public ResponseEntity<?> toggleAssetSubCategoryStatus(@RequestParam Long id) {
        return assetSubCategoryService.toggleAssetSubCategoryStatus(id);
    }

}
