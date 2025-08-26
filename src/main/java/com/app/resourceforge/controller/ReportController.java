package com.app.resourceforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.service.ReportService;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/getAssetReportTypeWish")
    public ResponseEntity<?> getAssetReportTypeWish() {
        return reportService.getAssetReportTypeWish();
    }

    @GetMapping(value = "/getAssetReportCategoryWish")
    public ResponseEntity<?> getAssetReportCategoryWish() {
        return reportService.getAssetReportCategoryWish();
    }

    @GetMapping(value = "/getAssetReportLocationWish")
    public ResponseEntity<?> getAssetReportLocationWish() {
        return reportService.getAssetReportLocationWish();
    }

    @GetMapping(value = "/getAssetReportForSale")
    public ResponseEntity<?> getAssetReportForSale() {
        return reportService.getAssetReportForSale();
    }
    @GetMapping(value = "/getAssetReportForSaleDetail")
    public ResponseEntity<?> getAssetReportForSaleDetail(@RequestParam String type) {
        return reportService.getAssetReportForSaleDetail(type);
    }

    @GetMapping(value = "/getAssetReportDetailsTypeWish")
    public ResponseEntity<?> getAssetReportDetailsTypeWish(@RequestParam String type) {
        return reportService.getAssetReportDetailsTypeWish(type);
    }

    @GetMapping(value = "/getAssetReportDetailsCategoryWish")
    public ResponseEntity<?> getAssetReportDetailsCategoryWish(@RequestParam String catId) {
        return reportService.getAssetReportDetailsCategoryWish(catId);
    }
    @GetMapping(value = "/getAssetReportDetailsLocationWish")
    public ResponseEntity<?> getAssetReportDetailsLocationWish(@RequestParam String location) {
        return reportService.getAssetReportDetailsLocationWish(location);
    }
    @GetMapping(value = "/getAssetReportDetailsEmployeeWish")
    public ResponseEntity<?> getAssetReportDetailsEmployeeWish(@RequestParam String empCode) {
        return reportService.getAssetReportDetailsEmployeeWish(empCode);
    }

    @GetMapping(value = "/yearCodeControl")
    public ResponseEntity<?> yearCodeControl() {
        return reportService.yearCodeControl();
    }

    
    @GetMapping(value = "/reportOnDepreciation")
    public ResponseEntity<?> reportOnDepreciation(@RequestParam String yearCode) {
        return reportService.reportOnDepreciation(yearCode);
    }

    @GetMapping(value = "/notification")
    public ResponseEntity<?> notification() {
        return reportService.notification();
    }

    @GetMapping(value = "/getAssetForProject")
    public ResponseEntity<?> getAssetForProject() {
        return reportService.getAssetForProject();
    }
    @GetMapping(value = "/getAssetsWithRemainingWarranty")
    public ResponseEntity<?> getAssetsWithRemainingWarranty() {
        return reportService.getAssetsWithRemainingWarranty();
    }
    @GetMapping(value = "/getAssetsWithRemainingAmc")
    public ResponseEntity<?> getAssetsWithRemainingAmc() {
        return reportService.getAssetsWithRemainingAmc();
    }
    @GetMapping(value = "/getAssetsWithRemainingInsurance")
    public ResponseEntity<?> getAssetsWithRemainingInsurance() {
        return reportService.getAssetsWithRemainingInsurance();
    }

    @GetMapping(value = "/getAssetDetails")
    public ResponseEntity<?> getAssetDetails() {
        return reportService.getAssetDetails();
    }
    @GetMapping(value = "/findAssetsWithLogLast7Days")
    public ResponseEntity<?> findAssetsWithLogLast7Days() {
        return reportService.findAssetsWithLogLast7Days();
    }
}
