package com.app.resourceforge.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.DTO.AssetForProjectDTO;
import com.app.resourceforge.DTO.AssetStatusDTO;
import com.app.resourceforge.DTO.LocationChangeDTO;
import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.AssetCustomerAssign;
import com.app.resourceforge.model.AssetDetails;

public interface AssetService {

        ResponseEntity<?> getAllAsset();

        ResponseEntity<?> getAssets();

        ResponseEntity<?> getAssetById(Long id);

        ResponseEntity<?> createAsset(@Valid Asset asset);

        ResponseEntity<?> updateAsset(@Valid Asset asset);

        ResponseEntity<?> toggleAssetActive(Long id);

        ResponseEntity<?> getAssetsForManagement();

        ResponseEntity<?> getAssetsForProjectManagement();

        ResponseEntity<?> updateAssetStatusToOutOfService(Long id, String type, String note, String location,
                        String subLocation, Long empId, String date, String buyerType, String buyer, Double soldValue);

        ResponseEntity<?> getAssetSalesValue(Long id);

        ResponseEntity<?> updateAssetStatus(AssetStatusDTO dto);

        ResponseEntity<?> getAssetIds();

        ResponseEntity<?> releaseReserveAsset(Long id);

        ResponseEntity<?> updateFinanceEntry(@Valid AssetDetails details);

        ResponseEntity<?> updateAssetInsuranceAmc(AssetStatusDTO dto);

        ResponseEntity<?> getAssetsForFinance();

        ResponseEntity<?> getFinanceByAssetId(Long id);

        ResponseEntity<?> updateFinanceEntryEdit(@Valid AssetDetails details);

        ResponseEntity<?> getAssetsForAMC();

        ResponseEntity<?> getAssetsForInsurance();

        ResponseEntity<?> getAssetCostAndPurchase(Long id);

        ResponseEntity<?> getAssetsForLogistics();

        ResponseEntity<?> getAssetsLogisticsUpdate(Long id, String date);

        ResponseEntity<?> getAssetsLogisticsDismiss(Long id);

        ResponseEntity<?> getAssetTagIdForQRCODE(String type, List<Long> ids);

        ResponseEntity<?> getAllAssetsLogisticsUpdate(List<Long> id, String date);

        ResponseEntity<?> getAllAssetsLogisticsDismiss(List<Long> id);

        ResponseEntity<?> updateAssetForProject(AssetForProjectDTO dto);

        ResponseEntity<?> updateAssetForProjectReturn(List<Long> id, String date);

        ResponseEntity<?> updateBlukAssetScrap(List<Long> id, String type, String note, String location,
                        String subLocation,
                        String date);

        ResponseEntity<?> locationUpdate(LocationChangeDTO dto);

        ResponseEntity<?> updateAssetRenting(Long id, String type, String note, String startDate, String endDate);

        ResponseEntity<?> assetReturn(Long id);

        ResponseEntity<?> dismissAsset(Long id);

        ResponseEntity<?> updateSerial(Long id, String serial);

        ResponseEntity<?> postCustomerAssign(AssetCustomerAssign dto);

        ResponseEntity<?> assetCustomerWork(Long id, String type);

}
