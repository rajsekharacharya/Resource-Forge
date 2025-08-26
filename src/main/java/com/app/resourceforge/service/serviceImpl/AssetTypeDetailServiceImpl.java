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

import com.app.resourceforge.model.AssetType;
import com.app.resourceforge.model.AssetTypeDetail;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetTypeDetailRepository;
import com.app.resourceforge.repository.AssetTypeRepository;
import com.app.resourceforge.service.AssetTypeDetailService;

@Service
public class AssetTypeDetailServiceImpl implements AssetTypeDetailService {

    @Autowired
    AssetTypeRepository assetTypeRepository;

    @Autowired
    AssetTypeDetailRepository assetTypeDetailRepository;

    @Override
    public ResponseEntity<?> getAllAssetTypeDetail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetTypeDetailRepository.findByCompanyIdAndSuperCompanyId(user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetTypeDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetTypeDetailRepository.findByStatusAndCompanyIdAndSuperCompanyId(true, user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetTypeDetailById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetTypeDetailRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createAssetTypeDetail(@Valid List<AssetTypeDetail> assetTypeDetail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        Optional<AssetType> findById = assetTypeRepository.findById(assetTypeDetail.get(0).getAssetTypeId());

        assetTypeDetail.forEach(detail -> {
            detail.setCompanyId(user.getCompanyId());
            detail.setSuperCompanyId(user.getSuperCompanyId());
            detail.setStatus(true);
            detail.setAssetType(findById.get());
        });

        assetTypeDetail.forEach(detail -> {
            detail.setModel(
                    generateNextDataEntry(detail.getAssetTypeId(), detail.getCompanyId(), detail.getSuperCompanyId()));
            assetTypeDetailRepository.save(detail);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    public String generateNextDataEntry(Long id, Integer companyId, Integer superCompanyId) {
        Optional<String> findMaxModel = assetTypeDetailRepository.findMaxModel(id, companyId, superCompanyId);
        if (findMaxModel.isPresent() && findMaxModel.get() != null) {
            int currentNumber = Integer.parseInt(findMaxModel.get().replace("data", ""));
            int nextNumber = currentNumber + 1;
            String formattedNextNumber = String.format("%02d", nextNumber);
            return "data" + formattedNextNumber;
        } else {
            return "data01";
        }
    }

    @Override
    public ResponseEntity<?> updateAssetTypeDetail(@Valid AssetTypeDetail assetTypeDetail) {
        assetTypeDetailRepository.save(assetTypeDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> toggleAssetTypeDetailStatus(Long id) {
        Optional<AssetTypeDetail> details = assetTypeDetailRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = details
                .map(loc -> {
                    loc.setStatus(!loc.isStatus());
                    assetTypeDetailRepository.save(loc);
                    return messages.get(loc.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
