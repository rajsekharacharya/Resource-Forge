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

import com.app.resourceforge.model.AssetType;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetTypeRepository;
import com.app.resourceforge.service.AssetTypeService;

@Service
public class AssetTypeServiceImpl implements AssetTypeService {

    @Autowired
    AssetTypeRepository assetTypeRepository;

    @Override
    public ResponseEntity<?> getAllAssetType() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(
                assetTypeRepository.findByCompanyIdAndSuperCompanyId(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getTotalAssetType() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(assetTypeRepository.findByStatusAndCompanyIdAndSuperCompanyId(true,
                user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetTypeById(Long id) {
        return assetTypeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> createAssetType(@Valid AssetType assetType) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        if (assetTypeRepository.existsByTypeAndCompanyIdAndSuperCompanyId(assetType.getType(), user.getCompanyId(),
                user.getSuperCompanyId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            assetType.setCompanyId(user.getCompanyId());
            assetType.setSuperCompanyId(user.getSuperCompanyId());
            assetType.setStatus(true);
            assetTypeRepository.save(assetType);
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }

    }

    @Override
    public ResponseEntity<?> updateAssetType(@Valid AssetType assetType) {
        assetTypeRepository.save(assetType);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> deleteAssetType(Long id) {
        Optional<AssetType> asset = assetTypeRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = asset
                .map(loc -> {
                    loc.setStatus(!loc.isStatus());
                    assetTypeRepository.save(loc);
                    return messages.get(loc.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

    @Override
    public ResponseEntity<?> getAssetTypeDropDown(String type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(assetTypeRepository.getDropDownDetails(type,
                user.getCompanyId(), user.getSuperCompanyId()));
    }

}
