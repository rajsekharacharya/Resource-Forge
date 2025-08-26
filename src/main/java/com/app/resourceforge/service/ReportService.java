package com.app.resourceforge.service;

import org.springframework.http.ResponseEntity;

public interface ReportService {

    ResponseEntity<?> getAssetReportTypeWish();

    ResponseEntity<?> getAssetReportCategoryWish();

    ResponseEntity<?> getAssetReportDetailsTypeWish(String type);

    ResponseEntity<?> getAssetReportDetailsCategoryWish(String catId);

    ResponseEntity<?> yearCodeControl();

    ResponseEntity<?> reportOnDepreciation(String yearCode);

    ResponseEntity<?> getAssetReportLocationWish();

    ResponseEntity<?> getAssetReportDetailsLocationWish(String location);

    ResponseEntity<?> getAssetReportDetailsEmployeeWish(String empCode);

    ResponseEntity<?> notification();

    ResponseEntity<?> getAssetReportForSale();

    ResponseEntity<?> getAssetReportForSaleDetail(String type);

    ResponseEntity<?> getAssetsWithRemainingWarranty();

    ResponseEntity<?> getAssetsWithRemainingAmc();

    ResponseEntity<?> getAssetsWithRemainingInsurance();

    ResponseEntity<?> getAssetForProject();

    ResponseEntity<?> getAssetDetails();

    ResponseEntity<?> findAssetsWithLogLast7Days();
    
}
