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

import com.app.resourceforge.model.AssetTypeDetailDes;
import com.app.resourceforge.service.AssetTypeDetailDesService;

@RestController
@RequestMapping("/assetTypeDetailDes")
public class AssetTypeDetailDesController {

    @Autowired
    AssetTypeDetailDesService assetTypeDetailDesService;

    @GetMapping(value = "/getAllAssetTypeDetailDes")
    public ResponseEntity<?> getAllAssetTypeDetailDes() {
        return assetTypeDetailDesService.getAllAssetTypeDetailDes();
    }

    @GetMapping(value = "/getAssetTypeDetailDess")
    public ResponseEntity<?> getAssetTypeDetailDess() {
        return assetTypeDetailDesService.getAssetTypeDetailDess();
    }

    @GetMapping(value = "/getAssetTypeDetailDesById")
    public ResponseEntity<?> getAssetTypeDetailDesById(@RequestParam Long id) {
        return assetTypeDetailDesService.getAssetTypeDetailDesById(id);
    }

    @PostMapping(value = "/createAssetTypeDetailDes")
    public ResponseEntity<?> createAssetTypeDetailDes(@RequestBody @Valid List<AssetTypeDetailDes> assetTypeDetailDes) {
        return assetTypeDetailDesService.createAssetTypeDetailDes(assetTypeDetailDes);
    }

    @PutMapping(value = "/updateAssetTypeDetailDes")
    public ResponseEntity<?> updateAssetTypeDetailDes(@RequestBody @Valid AssetTypeDetailDes assetTypeDetailDes) {
        return assetTypeDetailDesService.updateAssetTypeDetailDes(assetTypeDetailDes);
    }

    @DeleteMapping(value = "/toggleAssetTypeDetailDesStatus")
    public ResponseEntity<?> toggleAssetTypeDetailDesStatus(@RequestParam Long id) {
        return assetTypeDetailDesService.toggleAssetTypeDetailDesStatus(id);
    }

}
