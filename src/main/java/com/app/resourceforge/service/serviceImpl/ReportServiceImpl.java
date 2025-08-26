package com.app.resourceforge.service.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetAmcRepository;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

        @Autowired
        AssetRepository assetRepository;
        @Autowired
        AssetAmcRepository assetAmcRepository;

        @Override
        public ResponseEntity<?> getAssetReportTypeWish() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportTypeWish(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetReportCategoryWish() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetCategoryWish(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetReportDetailsTypeWish(String type) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportDetailsTypeWish(type, user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetReportDetailsCategoryWish(String catId) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportDetailsCategoryWish(catId, user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> yearCodeControl() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.yearCodeControl(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> reportOnDepreciation(String yearCode) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.reportOnDepreciation(user.getCompanyId(),
                                                user.getSuperCompanyId(), yearCode));
        }

        @Override
        public ResponseEntity<?> getAssetReportLocationWish() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportLocationWish(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetReportDetailsLocationWish(String location) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportDetailsLocationWish(location, user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetReportDetailsEmployeeWish(String empCode) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportDetailsEmployeeWish(empCode, user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> notification() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getNotification(LocalDate.now().toString(), user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetReportForSale() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportForSale(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetReportForSaleDetail(String type) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetReportForSaleDetail(type, user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetsWithRemainingWarranty() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetAmcRepository.findAssetsWithRemainingWarranty(LocalDate.now().toString(),
                                                user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetsWithRemainingAmc() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetAmcRepository.findAssetsWithRemainingAmc(LocalDate.now().toString(),
                                                user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetsWithRemainingInsurance() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetAmcRepository.findAssetsWithRemainingInsurance(LocalDate.now().toString(),
                                                user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetForProject() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getAssetForProject(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> getAssetDetails() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.getDetailAsset(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

        @Override
        public ResponseEntity<?> findAssetsWithLogLast7Days() {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                MyUserDetails user = (MyUserDetails) auth.getPrincipal();
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(assetRepository.findAssetsWithLogLast7Days(user.getCompanyId(),
                                                user.getSuperCompanyId()));
        }

}
