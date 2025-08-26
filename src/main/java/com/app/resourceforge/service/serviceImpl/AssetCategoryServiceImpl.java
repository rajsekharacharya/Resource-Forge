package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.AssetCategory;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetCategoryRepository;
import com.app.resourceforge.service.AssetCategoryService;

@Service
public class AssetCategoryServiceImpl implements AssetCategoryService {

    @Autowired
    AssetCategoryRepository assetCategoryRepository;

    @Override
    public ResponseEntity<?> getAllAssetCategory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetCategoryRepository.findByCompanyId(user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetCategorys() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetCategoryRepository.findByStatusAndCompanyId(true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetCategoryById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetCategoryRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createAssetCategory(@Valid AssetCategory assetCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (assetCategoryRepository.existsByName(assetCategory.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            assetCategory.setCompanyId(user.getCompanyId());
            assetCategory.setSuperCompanyId(user.getSuperCompanyId());
            assetCategory.setStatus(true);
            assetCategoryRepository.save(assetCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> updateAssetCategory(@Valid AssetCategory assetCategory) {
        assetCategoryRepository.save(assetCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleAssetCategoryStatus(Long id) {
        Optional<AssetCategory> assetCategory = assetCategoryRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = assetCategory
                .map(asset -> {
                    asset.setStatus(!asset.isStatus());
                    assetCategoryRepository.save(asset);
                    return messages.get(asset.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
