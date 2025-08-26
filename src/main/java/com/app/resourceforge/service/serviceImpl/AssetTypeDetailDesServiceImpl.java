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

import com.app.resourceforge.model.AssetTypeDetail;
import com.app.resourceforge.model.AssetTypeDetailDes;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetTypeDetailDesRepository;
import com.app.resourceforge.repository.AssetTypeDetailRepository;
import com.app.resourceforge.service.AssetTypeDetailDesService;

@Service
public class AssetTypeDetailDesServiceImpl implements AssetTypeDetailDesService {

    @Autowired
    AssetTypeDetailRepository assetTypeDetailRepository;
    @Autowired
    AssetTypeDetailDesRepository assetTypeDetailRepositoryDes;

    @Override
    public ResponseEntity<?> getAllAssetTypeDetailDes() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetTypeDetailRepositoryDes.findByCompanyIdAndSuperCompanyId(user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetTypeDetailDess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetTypeDetailRepositoryDes.findByStatusAndCompanyIdAndSuperCompanyId(true, user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetTypeDetailDesById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetTypeDetailRepositoryDes.findById(id));
    }

    @Override
    public ResponseEntity<?> createAssetTypeDetailDes(@Valid List<AssetTypeDetailDes> assetTypeDetailDes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<AssetTypeDetail> findById = assetTypeDetailRepository
                .findById(assetTypeDetailDes.get(0).getAssetTypeDetailId());
        assetTypeDetailDes.forEach(des -> {
            des.setCompanyId(user.getCompanyId());
            des.setSuperCompanyId(user.getSuperCompanyId());
            des.setValue(des.getCaption());
            des.setAssetTypeDetail(findById.get());
            des.setStatus(true);
        });
        assetTypeDetailRepositoryDes.saveAll(assetTypeDetailDes);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> updateAssetTypeDetailDes(@Valid AssetTypeDetailDes assetTypeDetailDes) {
        Optional<AssetTypeDetail> findById = assetTypeDetailRepository
                .findById(assetTypeDetailDes.getAssetTypeDetailId());
                assetTypeDetailDes.setAssetTypeDetail(findById.get());
        assetTypeDetailRepositoryDes.save(assetTypeDetailDes);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> toggleAssetTypeDetailDesStatus(Long id) {
        Optional<AssetTypeDetailDes> details = assetTypeDetailRepositoryDes.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = details
                .map(loc -> {
                    loc.setStatus(!loc.isStatus());
                    assetTypeDetailRepositoryDes.save(loc);
                    return messages.get(loc.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
