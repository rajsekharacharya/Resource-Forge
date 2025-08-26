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

import com.app.resourceforge.DTO.AssetForProjectDTO;
import com.app.resourceforge.DTO.AssetStatusDTO;
import com.app.resourceforge.DTO.LocationChangeDTO;
import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.AssetCustomerAssign;
import com.app.resourceforge.model.AssetDetails;
import com.app.resourceforge.service.AssetService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping(value = "/getAllAsset")
    public ResponseEntity<?> getAllAsset() {
        return assetService.getAllAsset();
    }

    @GetMapping(value = "/getAssets")
    public ResponseEntity<?> getAssets() {
        return assetService.getAssets();
    }

    @GetMapping(value = "/getAssetsForManagement")
    public ResponseEntity<?> getAssetsForManagement() {
        return assetService.getAssetsForManagement();
    }

    @GetMapping(value = "/getAssetsForProjectManagement")
    public ResponseEntity<?> getAssetsForProjectManagement() {
        return assetService.getAssetsForProjectManagement();
    }

    @GetMapping(value = "/getAssetsForFinance")
    public ResponseEntity<?> getAssetsForFinance() {
        return assetService.getAssetsForFinance();
    }

    @GetMapping(value = "/getAssetsForLogistics")
    public ResponseEntity<?> getAssetsForLogistics() {
        return assetService.getAssetsForLogistics();
    }

    @GetMapping(value = "/getAssetCostAndPurchase")
    public ResponseEntity<?> getAssetCostAndPurchase(@RequestParam Long id) {
        return assetService.getAssetCostAndPurchase(id);
    }

    @GetMapping(value = "/getAssetsForAMC")
    public ResponseEntity<?> getAssetsForAMC() {
        return assetService.getAssetsForAMC();
    }

    @GetMapping(value = "/getAssetsForInsurance")
    public ResponseEntity<?> getAssetsForInsurance() {
        return assetService.getAssetsForInsurance();
    }

    @GetMapping(value = "/getAssetById")
    public ResponseEntity<?> getAssetById(@RequestParam Long id) {
        return assetService.getAssetById(id);
    }

    @GetMapping(value = "/getFinanceByAssetId")
    public ResponseEntity<?> getFinanceByAssetId(@RequestParam Long id) {
        return assetService.getFinanceByAssetId(id);
    }

    @PostMapping(value = "/createAsset")
    public ResponseEntity<?> createAsset(@RequestBody @Valid Asset asset) {
        return assetService.createAsset(asset);
    }

    @PutMapping(value = "/updateFinanceEntry")
    public ResponseEntity<?> updateFinanceEntry(@RequestBody @Valid AssetDetails details) {
        return assetService.updateFinanceEntry(details);
    }

    @PutMapping(value = "/updateFinanceEntryEdit")
    public ResponseEntity<?> updateFinanceEntryEdit(@RequestBody @Valid AssetDetails details) {
        return assetService.updateFinanceEntryEdit(details);
    }

    @PutMapping(value = "/updateAsset")
    public ResponseEntity<?> updateAsset(@RequestBody @Valid Asset asset) {
        return assetService.updateAsset(asset);
    }

    @PutMapping(value = "/updateAssetForProject")
    public ResponseEntity<?> updateAssetForProject(@RequestBody AssetForProjectDTO dto) {
        return assetService.updateAssetForProject(dto);
    }

    @PutMapping(value = "/updateAssetForProjectReturn")
    public ResponseEntity<?> updateAssetForProjectReturn(@RequestParam List<Long> id, @RequestParam String date) {
        return assetService.updateAssetForProjectReturn(id, date);
    }

    @PutMapping(value = "/getAssetsLogisticsUpdate")
    public ResponseEntity<?> getAssetsLogisticsUpdate(@RequestParam Long id, @RequestParam String date) {
        return assetService.getAssetsLogisticsUpdate(id, date);
    }

    @PutMapping(value = "/getAllAssetsLogisticsUpdate")
    public ResponseEntity<?> getAllAssetsLogisticsUpdate(@RequestParam(required = false) List<Long> id,
            @RequestParam String date) {
        return assetService.getAllAssetsLogisticsUpdate(id, date);
    }

    @PutMapping(value = "/getAssetsLogisticsDismiss")
    public ResponseEntity<?> getAssetsLogisticsDismiss(@RequestParam Long id) {
        return assetService.getAssetsLogisticsDismiss(id);
    }

    @PutMapping(value = "/getAllAssetsLogisticsDismiss")
    public ResponseEntity<?> getAllAssetsLogisticsDismiss(@RequestParam(required = false) List<Long> id) {
        return assetService.getAllAssetsLogisticsDismiss(id);
    }

    @DeleteMapping(value = "/toggleAssetActive")
    public ResponseEntity<?> toggleAssetActive(@RequestParam Long id) {
        return assetService.toggleAssetActive(id);
    }
    @DeleteMapping(value = "/dismissAsset")
    public ResponseEntity<?> dismissAsset(@RequestParam Long id) {
        return assetService.dismissAsset(id);
    }

    @PutMapping(value = "/updateSerial")
    public ResponseEntity<?> updateSerial(@RequestParam Long id,@RequestParam String serial) {
        return assetService.updateSerial(id,serial);
    }

    @GetMapping(value = "/getAssetSalesValue")
    public ResponseEntity<?> getAssetSalesValue(@RequestParam Long id) {
        return assetService.getAssetSalesValue(id);
    }

    @PutMapping(value = "/updateAssetStatusToOutOfService")
    public ResponseEntity<?> updateAssetStatusToOutOfService(@RequestParam Long id, @RequestParam String type,
            @RequestParam(required = false) String note, @RequestParam String location,
            @RequestParam(required = false) String subLocation,
            @RequestParam(required = false) Long empId, @RequestParam String date,
            @RequestParam(required = false) String buyerType, @RequestParam(required = false) String buyer,
            @RequestParam(required = false) Double soldValue) {
        return assetService.updateAssetStatusToOutOfService(id, type, note, location, subLocation, empId, date,
                buyerType, buyer, soldValue);
    }
    @PutMapping(value = "/updateAssetRenting")
    public ResponseEntity<?> updateAssetRenting(@RequestParam Long id, @RequestParam String type,
            @RequestParam(required = false) String note,  @RequestParam String startDate,
            @RequestParam(required = false) String endDate) {
        return assetService.updateAssetRenting(id, type, note, startDate, endDate);
    }

    @PutMapping(value = "/updateBlukAssetScrap")
    public ResponseEntity<?> updateBlukAssetScrap(@RequestParam List<Long> id, @RequestParam String type,
            @RequestParam(required = false) String note, @RequestParam String location,
            @RequestParam(required = false) String subLocation,
            @RequestParam String date) {
        return assetService.updateBlukAssetScrap(id, type, note, location, subLocation, date);
    }

    @Operation(summary = "Update Asset Status Universally", description = "1. For Assign Use Type AS 'A'\n" +
            "2. For HandOver Use Type AS 'HO'\n" +
            "3. For Repair In Use Type AS 'RI'\n" +
            "4. For Repair Out Type AS 'RO'\n" +
            "5. For Sales Use Type AS 'SS'\n" +
            "5. For Scrape Use Type AS 'SC'\n")
    @PutMapping(value = "/updateAssetStatus")
    public ResponseEntity<?> updateAssetStatus(@RequestBody AssetStatusDTO dto) {
        return assetService.updateAssetStatus(dto);
    }

    @Operation(summary = "Update Asset Status Universally")
    @PutMapping(value = "/updateAssetInsuranceAmc")
    public ResponseEntity<?> updateAssetInsuranceAmc(@RequestBody AssetStatusDTO dto) {
        return assetService.updateAssetInsuranceAmc(dto);
    }

    @GetMapping(value = "/releaseReserveAsset")
    public ResponseEntity<?> releaseReserveAsset(@RequestParam Long id) {
        return assetService.releaseReserveAsset(id);
    }
    @PutMapping(value = "/assetReturn")
    public ResponseEntity<?> assetReturn(@RequestParam Long id) {
        return assetService.assetReturn(id);
    }

    @GetMapping(value = "/getAssetIds")
    public ResponseEntity<?> getAssetIds() {
        return assetService.getAssetIds();
    }

    @GetMapping(value = "/getAssetTagIdForQRCODE")
    public ResponseEntity<?> getAssetTagIdForQRCODE(@RequestParam String type, @RequestParam List<Long> ids) {
        return assetService.getAssetTagIdForQRCODE(type, ids);
    }

    @PutMapping(value = "/locationUpdate")
    public ResponseEntity<?> locationUpdate(@RequestBody LocationChangeDTO dto) {
        return assetService.locationUpdate(dto);
    }
    @PostMapping(value = "/postCustomerAssign")
    public ResponseEntity<?> postCustomerAssign(@RequestBody AssetCustomerAssign dto) {
        return assetService.postCustomerAssign(dto);
    }
    @PutMapping(value = "/assetCustomerWork")
    public ResponseEntity<?> assetCustomerWork(@RequestParam String type, @RequestParam Long id) {
        return assetService.assetCustomerWork(id,type);
    }
}
