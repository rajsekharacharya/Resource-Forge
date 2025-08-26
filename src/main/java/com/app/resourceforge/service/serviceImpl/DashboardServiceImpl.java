package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.repository.CallLogRepository;
import com.app.resourceforge.repository.SuperCompanyRepository;
import com.app.resourceforge.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    SuperCompanyRepository superCompanyRepository;
    @Autowired
    CallLogRepository callLogRepository;

    @Override
    public ResponseEntity<?> getDashboardForSAAS() {

        Map<String, List<Map<String, Object>>> data = new HashMap<>();
        data.put("CompanyData", superCompanyRepository.getSuperCompanyDetails());
        data.put("CompanyAssetData", superCompanyRepository.getAssetCountPerCompany());
        data.put("PlanSummery", superCompanyRepository.getPlanSummery());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(data);
    }

    @Override
    public ResponseEntity<?> getDashboardForSuperAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Map<String, List<Map<String, Object>>> data = new HashMap<>();
        data.put("BlockData", assetRepository.getAssetMiniDetailForSuperadmin(user.getSuperCompanyId()));
        data.put("TypeWish", assetRepository.getAssetTypeWishForSuperadmin(user.getSuperCompanyId()));
        data.put("StatusWish", assetRepository.getAssetStatusWishForSuperAdmin(user.getSuperCompanyId()));
        data.put("DeptWish", assetRepository.getAssetDepartmentWishForSuperadmin(user.getSuperCompanyId()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(data);
    }

    @Override
    public ResponseEntity<?> getDashboardForAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Map<String, Object> data = new HashMap<>();
        Map<String, Integer> notification = new HashMap<>();

        notification.put("AMC", assetRepository.getAMCCountNotification(user.getCompanyId(), user.getSuperCompanyId()));
        notification.put("Warranty",
                assetRepository.getWarrantyCountNotification(user.getCompanyId(), user.getSuperCompanyId()));
        notification.put("Project",
                assetRepository.getProjectCountNotification(user.getCompanyId(), user.getSuperCompanyId()));
        // notification.put("Rent",
        // assetRepository.getRentCountNotification(user.getCompanyId(),
        // user.getSuperCompanyId()));
        notification.put("Insurance",
                assetRepository.getInsuranceCountNotification(user.getCompanyId(), user.getSuperCompanyId()));
        notification.put("Log", assetRepository.countVersionsLast7Days(user.getCompanyId(), user.getSuperCompanyId()));

        data.put("BlockData", assetRepository.getAssetMiniDetail(user.getCompanyId(), user.getSuperCompanyId()));
        data.put("TypeWish", assetRepository.getAssetTypeWish(user.getCompanyId(), user.getSuperCompanyId()));
        data.put("StatusWish", assetRepository.getAssetStatusWish(user.getCompanyId(), user.getSuperCompanyId()));
        data.put("DeptWish", assetRepository.getAssetDepartmentWish(user.getCompanyId(), user.getSuperCompanyId()));
        data.put("notification", notification);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(data);
    }

    @Override
    public ResponseEntity<?> getCompanyAndSuperCompany() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Map<String, Integer> data = new HashMap<>();
        data.put("SuperCompanyId", user.getSuperCompanyId());
        data.put("CompanyId", user.getCompanyId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(data);
    }

    @Override
    public ResponseEntity<?> getDashboardForServiceEngineer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        Optional<Integer> assignCall = callLogRepository.assignCall(user.getUser().getId());
        Optional<Integer> call = callLogRepository.resolveCall(user.getUser().getId());
        Optional<Integer> pendingCall = callLogRepository.pendingCall(user.getUser().getId());
        Optional<Integer> dismissCall = callLogRepository.dismissCall(user.getUser().getId());

        Map<String, Integer> data = new HashMap<>();
        data.put("assignCall", assignCall.get());
        data.put("resolveCall", call.get());
        data.put("pendingCall", pendingCall.get());
        data.put("dismissCall", dismissCall.get());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(data);
    }

}
