package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
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
import com.app.resourceforge.model.AssetSubCategory;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetCategoryRepository;
import com.app.resourceforge.repository.AssetSubCategoryRepository;
import com.app.resourceforge.service.AssetSubCategoryService;

@Service
public class AssetSubCategoryServiceImpl implements AssetSubCategoryService {

    @Autowired
    private AssetSubCategoryRepository assetSubCategoryRepository;

    @Autowired
    private AssetCategoryRepository assetCategoryRepository;

    @Override
    public ResponseEntity<?> getAllAssetSubCategory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetSubCategoryRepository.findByCompanyId(user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetSubCategorys() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetSubCategoryRepository.findByStatusAndCompanyId(true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetSubCategoryById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetSubCategoryRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createAssetSubCategory(@Valid List<AssetSubCategory> assetSubCategory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        Optional<AssetCategory> findById = assetCategoryRepository
                .findById(assetSubCategory.get(0).getAssetCategoryId());

        assetSubCategory.forEach(x -> {

            if (!assetSubCategoryRepository.existsByCompanyIdAndNameAndAssetCategoryId(user.getCompanyId(), x.getName(),
                    x.getAssetCategoryId())) {

                x.setCompanyId(user.getCompanyId());
                x.setSuperCompanyId(user.getSuperCompanyId());
                x.setAssetCategory(findById.get());
                x.setStatus(true);
                assetSubCategoryRepository.save(x);

            }

        });

        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> updateAssetSubCategory(@Valid AssetSubCategory assetSubCategory) {
        assetSubCategory
                .setAssetCategory(assetCategoryRepository.findById(assetSubCategory.getAssetCategoryId()).get());
        assetSubCategoryRepository.save(assetSubCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> toggleAssetSubCategoryStatus(Long id) {
        Optional<AssetSubCategory> assetSubCategory = assetSubCategoryRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = assetSubCategory
                .map(asset -> {
                    asset.setStatus(!asset.isStatus());
                    assetSubCategoryRepository.save(asset);
                    return messages.get(asset.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
