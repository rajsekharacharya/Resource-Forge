package com.app.resourceforge.service.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.DTO.AssetForProjectDTO;
import com.app.resourceforge.DTO.AssetStatusDTO;
import com.app.resourceforge.DTO.DepreciationEntry;
import com.app.resourceforge.DTO.LocationChangeDTO;
import com.app.resourceforge.model.ApplicationSetup;
import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.AssetAmc;
import com.app.resourceforge.model.AssetAssignHistory;
import com.app.resourceforge.model.AssetCustomerAssign;
import com.app.resourceforge.model.AssetDeployment;
import com.app.resourceforge.model.AssetDepreciation;
import com.app.resourceforge.model.AssetDetails;
import com.app.resourceforge.model.AssetInsurance;
import com.app.resourceforge.model.AssetMaintenances;
import com.app.resourceforge.model.AssetProject;
import com.app.resourceforge.model.AssetRent;
import com.app.resourceforge.model.CallLog;
import com.app.resourceforge.model.Employees;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.ProjectMaster;
import com.app.resourceforge.model.Settings;
import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.model.SystemInfo;
import com.app.resourceforge.repository.ApplicationSetupRepository;
import com.app.resourceforge.repository.AssetAmcRepository;
import com.app.resourceforge.repository.AssetAssignHistoryRepository;
import com.app.resourceforge.repository.AssetCategoryRepository;
import com.app.resourceforge.repository.AssetCustomerAssignRepository;
import com.app.resourceforge.repository.AssetDeploymentRepository;
import com.app.resourceforge.repository.AssetDepreciationRepository;
import com.app.resourceforge.repository.AssetDetailsRepository;
import com.app.resourceforge.repository.AssetInsuranceRepository;
import com.app.resourceforge.repository.AssetMaintenancesRepository;
import com.app.resourceforge.repository.AssetProjectRepository;
import com.app.resourceforge.repository.AssetRentRepository;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.repository.AssetTypeRepository;
import com.app.resourceforge.repository.CallLogRepository;
import com.app.resourceforge.repository.EmployeesRepository;
import com.app.resourceforge.repository.ProjectMasterRepository;
import com.app.resourceforge.repository.SettingsRepository;
import com.app.resourceforge.repository.SubscriptionPlanRepository;
import com.app.resourceforge.repository.SystemInfoRepository;
import com.app.resourceforge.service.AssetService;
import com.app.resourceforge.util.DayDepreciationCalculator;
import com.app.resourceforge.util.DepreciationCalculator;
import com.app.resourceforge.util.DepreciationCalculatorBetween;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;
    @Autowired
    private AssetDetailsRepository assetDetailsRepository;
    @Autowired
    private AssetDepreciationRepository assetDepreciationRepository;
    @Autowired
    private AssetAssignHistoryRepository assetAssignHistoryRepository;
    @Autowired
    private AssetMaintenancesRepository assetMaintenancesRepository;
    @Autowired
    private AssetDeploymentRepository assetDeploymentRepository;
    @Autowired
    SubscriptionPlanRepository subscriptionPlanRepository;
    @Autowired
    ApplicationSetupRepository applicationSetupRepository;
    @Autowired
    AssetCategoryRepository assetCategoryRepository;
    @Autowired
    AssetTypeRepository assetTypeRepository;
    @Autowired
    AssetAmcRepository assetAmcRepository;
    @Autowired
    AssetInsuranceRepository assetInsuranceRepository;
    @Autowired
    ProjectMasterRepository projectMasterRepository;
    @Autowired
    AssetProjectRepository assetProjectRepository;
    @Autowired
    AssetRentRepository assetRentRepository;
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    SettingsRepository settingsRepository;
    @Autowired
    CallLogRepository callLogRepository;
    @Autowired
    SystemInfoRepository systemInfoRepository;
    @Autowired
    AssetCustomerAssignRepository assetCustomerAssignRepository;

    /*
     * Edit Required -R
     * Work In Progress - WIP
     * Ready To Use - RTU
     * Reserved - RES
     * Assigned - ASS
     * In Repair - IR
     * Deployed - DEP
     * Sales - SAL
     * Scrap - SCR
     * Report Stolen - RS
     */

    /*
     * In-Transit
     * Pre-Entry
     * Active
     * Reserved
     * Assigned
     * MAPPED
     * Deployed
     * In Repair
     * Sale
     * Scrap
     * Stolen
     */

    @Override
    public ResponseEntity<?> getAllAsset() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.getAllAssets(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssets() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.findByActiveAndCompanyId(true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetsForManagement() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.getAssetsForManagement(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetsForProjectManagement() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.getAssetsForProjectManagement(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetsForLogistics() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.geAssetForLogistics(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetsForFinance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.getAssetsForFinance(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetCostAndPurchase(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.getAssetCostAndPurchase(id, user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetsForAMC() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.getAssetsForAMC(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetsForInsurance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetRepository.getAssetsForInsurance(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getAssetById(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        Optional<SubscriptionPlan> subscription = subscriptionPlanRepository.findByStatusAndSuperCompanyId(true,
                user.getSuperCompanyId());
        if (subscription.isPresent()) {
            Optional<Asset> findById = assetRepository.findById(id);
            findById.get().getAssetAssignHistory().forEach(x -> {
                if (x.isStatus()) {
                    Optional<Employees> byId = employeesRepository.findById(x.getEmpId());
                    findById.get().setAssignPicture(byId.get().getImageLink());
                }
            });
            findById.get().setDocUpload(subscription.get().isDocUpload());
            if (findById.get().getStatus().equals("In-Transit")) {
                findById.get().setInTransit(true);
            }

            if (findById.get().getSystemApplication() != null) {
                Optional<SystemInfo> application = systemInfoRepository.findById(findById.get().getSystemApplication());
                if (application.isPresent()) {
                    findById.get().setInstalledSoftware(application.get().getInstalledSoftware());
                    findById.get().setBios(application.get().getBios());
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(findById);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Subscription Not Active");
        }

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public ResponseEntity<?> createAsset(@Valid Asset asset) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<SubscriptionPlan> subscription = subscriptionPlanRepository.findByStatusAndSuperCompanyId(true,
                user.getSuperCompanyId());

        int assetCount = assetRepository.countBySuperCompanyId(user.getSuperCompanyId());

        if (subscription.isEmpty() || subscription.get().getAsset() <= assetCount) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Asset limit exceeded");
        }

        Optional<ApplicationSetup> serialKey = applicationSetupRepository
                .findByKeyAndStatusAndSuperCompanyIdAndCompanyId(
                        "COMPANY SHORT NAME", true, user.getSuperCompanyId(), user.getCompanyId());

        if (serialKey.isEmpty() || serialKey.get().getValue().isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please Set Company Short Name");
        }

        Map<Integer, String> settingValues = settingsRepository.findBySuperCompanyId(user.getSuperCompanyId())
                .stream().collect(Collectors.toMap(Settings::getPosition, Settings::getName));

        for (String serial : asset.getSerialNos()) {
            if (asset.isMultiProductStatus()) {
                if (assetRepository.existsBySerialNoAndCompanyIdAndSuperCompanyId(serial, user.getCompanyId(),
                        user.getSuperCompanyId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Duplicate Serial Number: " + serial);
                }
                createMultiLicenseAssets(asset, serialKey.get(), settingValues, user, serial);
            } else {
                if (assetRepository.existsBySerialNoAndCompanyIdAndSuperCompanyId(serial, user.getCompanyId(),
                        user.getSuperCompanyId())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Duplicate Serial Number: " + serial);
                }

                createSingleAsset(asset, serialKey.get(), settingValues, user, serial);
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    private void createMultiLicenseAssets(Asset asset, ApplicationSetup serialKey, Map<Integer, String> settingValues,
            MyUserDetails user, String serial) {
        Optional<ApplicationSetup> assetId = applicationSetupRepository.findByKeyAndStatusAndSuperCompanyIdAndCompanyId(
                "ASSET ID COUNT", true, user.getSuperCompanyId(), user.getCompanyId());

        String baseTagId = asset.isAssetTagIdStatus() ? asset.getAssetTagId()
                : generateTagId(asset, serialKey, assetId, settingValues);

        for (int i = 1; i <= asset.getLicense(); i++) {
            String subSerial = serial.trim() + "(L" + i + ")";
            if (!assetRepository.existsBySerialNoAndCompanyIdAndSuperCompanyId(subSerial, user.getCompanyId(),
                    user.getSuperCompanyId())) {
                Asset newAsset = createAssetCopy(asset, subSerial, baseTagId + "(L" + i + ")", i, user);
                assetRepository.save(newAsset);
            }
        }

        incrementAssetIdIfNeeded(asset, assetId);
    }

    private void createSingleAsset(Asset asset, ApplicationSetup serialKey, Map<Integer, String> settingValues,
            MyUserDetails user, String serial) {
        if (!assetRepository.existsBySerialNoAndCompanyIdAndSuperCompanyId(serial.trim(), user.getCompanyId(),
                user.getSuperCompanyId()) || "Pre-Entry".equals(asset.getStatus())) {
            Optional<ApplicationSetup> assetId = applicationSetupRepository
                    .findByKeyAndStatusAndSuperCompanyIdAndCompanyId(
                            "ASSET ID COUNT", true, user.getSuperCompanyId(), user.getCompanyId());

            String tagId = asset.isAssetTagIdStatus() ? asset.getAssetTagId()
                    : generateTagId(asset, serialKey, assetId, settingValues);
            Asset newAsset = createAssetCopy(asset, serial.trim(), tagId, null, user);
            assetRepository.save(newAsset);

            incrementAssetIdIfNeeded(asset, assetId);
        }
    }

    private Asset createAssetCopy(Asset source, String serial, String tagId, Integer license, MyUserDetails user) {
        Asset asset = new Asset();
        asset.copyFromAsset(source);
        if (!source.isWarrantyStatus()) {
            asset.setWarrantyStartDate(null);
            asset.setWarrantyEndDate(null);
        }
        asset.setSerialNo(serial);
        asset.setAssetTagId(tagId);
        asset.setLicense(license);
        asset.setCompanyId(user.getCompanyId());
        asset.setSuperCompanyId(user.getSuperCompanyId());
        if (source.isInTransit()) {

            asset.setStatus("In-Transit");
            asset.setPreStatus("In-Transit");
        } else {
            asset.setStatus("Active");
            asset.setPreStatus("Active");
        }
        return asset;
    }

    private String generateTagId(Asset asset, ApplicationSetup serialKey, Optional<ApplicationSetup> assetId,
            Map<Integer, String> settingValues) {
        StringBuilder tagId = new StringBuilder();

        settingValues.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String value = entry.getValue();
                    switch (value) {
                        case "COMPANY":
                            tagId.append(serialKey.getValue()).append("/");
                            break;
                        case "YEARCODE":
                            try {
                                String code = generateFiscalYearCode(LocalDate.now().toString());
                                if (code != null) {
                                    tagId.append(code).append("/");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "ASSET":
                            if (asset.getType() != null) {
                                tagId.append(asset.getType()).append("/");
                            }
                            break;
                        case "ID":
                            if (assetId.isPresent()) {
                                tagId.append(assetId.get().getValue());
                            }
                            break;
                        default:
                            // do nothing
                    }
                });

        return tagId.toString();
    }

    private void incrementAssetIdIfNeeded(Asset asset, Optional<ApplicationSetup> assetId) {
        if (!asset.isAssetTagIdStatus() && assetId.isPresent()) {
            int nextValue = Integer.parseInt(assetId.get().getValue()) + 1;
            assetId.get().setValue(String.valueOf(nextValue));
            applicationSetupRepository.save(assetId.get());
        }
    }

    @Override
    public ResponseEntity<?> updateAsset(@Valid Asset asset) {
        if (asset.getStatus().equals("Pre-Entry")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails user = (MyUserDetails) auth.getPrincipal();

            Optional<SubscriptionPlan> subscription = subscriptionPlanRepository.findByStatusAndSuperCompanyId(true,
                    user.getSuperCompanyId());

            int assetCount = assetRepository.countBySuperCompanyId(user.getSuperCompanyId());

            if (subscription.isEmpty() || subscription.get().getAsset() <= assetCount) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Asset limit exceeded");
            }

            Optional<ApplicationSetup> serialKey = applicationSetupRepository
                    .findByKeyAndStatusAndSuperCompanyIdAndCompanyId(
                            "COMPANY SHORT NAME", true, user.getSuperCompanyId(), user.getCompanyId());

            if (serialKey.isEmpty() || serialKey.get().getValue().isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please Set Company Short Name");
            }

            Map<Integer, String> settingValues = settingsRepository.findBySuperCompanyId(user.getSuperCompanyId())
                    .stream().collect(Collectors.toMap(Settings::getPosition, Settings::getName));

            createSingleAsset(asset, serialKey.get(), settingValues, user, asset.getSerialNo());

            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }

        assetRepository.save(asset);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleAssetActive(Long id) {
        Optional<Asset> assets = assetRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = assets
                .map(asset -> {
                    asset.setActive(!asset.isActive());
                    assetRepository.save(asset);
                    return messages.get(asset.isActive());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

    @Override
    public ResponseEntity<?> updateAssetStatus(AssetStatusDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (dto.getType().isBlank()) {
            return ResponseEntity.badRequest().body("System Error");
        }

        Optional<Asset> asset = assetRepository.findById(dto.getAssetId());
        if (asset.isPresent()) {
            String type = dto.getType();
            switch (type) {
                case "Reserve":
                    asset.get().setPreStatus(asset.get().getStatus());
                    asset.get().setStatus("Reserve");
                    asset.get().setReserveDept(dto.getReserveDept());
                    asset.get().setNote(dto.getNote());
                    assetRepository.save(asset.get());
                    return ResponseEntity.status(HttpStatus.CREATED).body("Asset Reserved ");
                case "Assigned":
                    AssetAssignHistory history = new AssetAssignHistory();
                    history.setAssignDate(LocalDate.now().toString());
                    history.setAssignLocation(dto.getLocationName());
                    history.setAssignSubLocation(dto.getSubLocationName());
                    history.setEmpId(dto.getEmployeeId());
                    history.setEmployeeCode(dto.getEmployeeCode());
                    history.setEmployee(dto.getEmployeeName());
                    history.setDepartment(dto.department);
                    history.setHandoverDate(dto.endDate);
                    history.setSuperCompanyId(user.getSuperCompanyId());
                    history.setCompanyId(user.getCompanyId());
                    history.setAsset(asset.get());
                    history.setStatus(true);
                    history.setAssignDoc(dto.getLink());
                    assetAssignHistoryRepository.save(history);

                    asset.get().setPreStatus(asset.get().getStatus());
                    asset.get().setLocation(dto.getLocationName());
                    asset.get().setSubLocation(dto.getSubLocationName());
                    asset.get().setStatus("Assigned");
                    if (asset.get().getReserveDept() != null) {
                        asset.get().setReserveDept(null);
                    }
                    asset.get().setNote(dto.getNote());
                    assetRepository.save(asset.get());
                    return ResponseEntity.status(HttpStatus.CREATED).body("Assigned to " + dto.getEmployeeName());

                case "Handover":
                    Optional<AssetAssignHistory> findAssign = assetAssignHistoryRepository
                            .findAssign(user.getCompanyId(), user.getSuperCompanyId(), asset.get().getId());
                    if (findAssign.isPresent()) {
                        findAssign.get().setHandoverDate(dto.getEndDate());
                        findAssign.get().setStatus(false);
                        findAssign.get().setHandoverLocation(dto.getLocationName());
                        findAssign.get().setHandoverSubLocation(dto.getSubLocationName());
                        findAssign.get().setHandoverEmployee(dto.getEmployeeName());
                        findAssign.get().setHandoverEmployeeCode(dto.getEmployeeCode());
                        findAssign.get().setHandoverDoc(dto.getLink());
                        assetAssignHistoryRepository.save(findAssign.get());

                        asset.get().setPreStatus(asset.get().getStatus());
                        asset.get().setNote(dto.getNote());
                        asset.get().setLocation(dto.getLocationName());
                        asset.get().setSubLocation(dto.getSubLocationName());
                        asset.get().setStatus("Active");
                        assetRepository.save(asset.get());
                    }
                    return ResponseEntity.status(HttpStatus.CREATED).body("Asset Ready To Use");

                case "Deployed":
                    AssetDeployment deployment = new AssetDeployment();
                    deployment.setNote(dto.getNote());
                    deployment.setLocationId(dto.getLocationId());
                    deployment.setLocationName(dto.getLocationName());
                    deployment.setSubLocationId(dto.getSubLocationId());
                    deployment.setSubLocationName(dto.getSubLocationName());
                    deployment.setStartDate(dto.getStartDate());
                    deployment.setEndDate(dto.getEndDate());
                    deployment.setSuperCompanyId(user.getSuperCompanyId());
                    deployment.setCompanyId(user.getCompanyId());
                    deployment.setAsset(asset.get());
                    deployment.setStatus(true);
                    assetDeploymentRepository.save(deployment);

                    asset.get().setPreStatus(asset.get().getStatus());
                    asset.get().setStatus("Deployed");
                    asset.get().setLocation(dto.getLocationName());
                    asset.get().setSubLocation(dto.getSubLocationName());
                    asset.get().setNote(dto.getNote());
                    assetRepository.save(asset.get());
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body("Asset Deployed in " + dto.getLocationName());

                case "Decommissioning":
                    Optional<AssetDeployment> findDeployment = assetDeploymentRepository
                            .findDeployment(user.getCompanyId(), user.getSuperCompanyId(), asset.get().getId());
                    if (findDeployment.isPresent()) {
                        findDeployment.get().setEndDate(dto.getEndDate());
                        findDeployment.get().setStatus(false);
                        assetDeploymentRepository.save(findDeployment.get());

                        asset.get().setPreStatus(asset.get().getStatus());
                        asset.get().setNote(dto.getNote());
                        asset.get().setStatus("Active");
                        assetRepository.save(asset.get());
                    }
                    return ResponseEntity.status(HttpStatus.CREATED).body("Asset Ready To Use");

                case "Maintenance":
                    AssetMaintenances maintenance = new AssetMaintenances();
                    maintenance.setAsset(asset.get());
                    maintenance.setCompanyId(user.getCompanyId());
                    maintenance.setSuperCompanyId(user.getSuperCompanyId());
                    maintenance.setStartDate(LocalDate.now().toString());
                    maintenance.setSupplier(dto.getSupplierId());
                    maintenance.setSupplierName(dto.getSupplierName());
                    maintenance.setType(dto.getMaintenanceType());
                    maintenance.setStatus(true);
                    maintenance.setStartNote(dto.note);
                    assetMaintenancesRepository.save(maintenance);

                    asset.get().setPreStatus(asset.get().getStatus());
                    asset.get().setStatus("Maintenance");
                    assetRepository.save(asset.get());

                    Optional<CallLog> log = callLogRepository.findByIdAndCompanyIdAndSuperCompanyId(dto.getCallId(),
                            user.getCompanyId(), user.getSuperCompanyId());
                    log.get().setStatus("MAINTENANCE");
                    callLogRepository.save(log.get());

                    return ResponseEntity.status(HttpStatus.CREATED).body("Asset In Maintenance");
                case "Repair Out":
                    Optional<AssetMaintenances> findRepair = assetMaintenancesRepository.findRepair(user.getCompanyId(),
                            user.getSuperCompanyId(), asset.get().getId());
                    if (findRepair.isPresent()) {
                        findRepair.get().setEndDate(LocalDate.now().toString());
                        findRepair.get().setStatus(false);
                        findRepair.get().setEndNote(dto.getNote());
                        findRepair.get().setAmount(dto.getAmount());
                        assetMaintenancesRepository.save(findRepair.get());

                        asset.get().setStatus(asset.get().getPreStatus());
                        assetRepository.save(asset.get());

                        Optional<CallLog> logR = callLogRepository.findByIdAndCompanyIdAndSuperCompanyId(
                                dto.getCallId(),
                                user.getCompanyId(), user.getSuperCompanyId());
                        logR.get().setActive(false);
                        logR.get().setStatus("RESOLVED");
                        callLogRepository.save(logR.get());

                        if (findRepair.get().getType().equals("Repair")) {

                            return ResponseEntity.status(HttpStatus.CREATED).body("Repair Completed");
                        } else {
                            return ResponseEntity.status(HttpStatus.CREATED)
                                    .body("Upgradation Completed. Please update data in Asset Master");
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Maintenance Data Not Found");
                    }

                default:
                    return ResponseEntity.badRequest().body("Invalid Type");
            }
        } else {
            return ResponseEntity.badRequest().body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> getAssetSalesValue(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);

        if (asset.get().isFinanceDetails()) {
            AssetDetails assetDetails = asset.get().getAssetDetails();
            double saleValue = 0.00;

            if (assetDetails.getDepreciableMethod().equals("SLD")) {
                saleValue = DayDepreciationCalculator.calculateStraightLine(
                        assetDetails.getCost(),
                        assetDetails.getSalvageValue(), assetDetails.getAssetLife(),
                        LocalDate.parse(assetDetails.getPutToUseDate()), LocalDate.now());
            } else {
                saleValue = DayDepreciationCalculator.calculateWDV(
                        assetDetails.getCost(),
                        assetDetails.getSalvageValue(), assetDetails.getAssetLife(),
                        LocalDate.parse(assetDetails.getPutToUseDate()), LocalDate.now());
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Asset Value: " + String.format("%.2f", saleValue));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("Asset Value Not available");
        }
    }

    @Override
    public ResponseEntity<?> updateAssetStatusToOutOfService(Long id, String type, String note, String location,
            String subLocation,
            Long empId, String date, String buyerType, String buyer, Double soldValue) {
        Optional<Asset> asset = assetRepository.findById(id);
        if (asset.isPresent()) {
            switch (type) {
                case "Sale":
                    asset.get().setPreStatus(asset.get().getStatus());
                    asset.get().setStatus(type);
                    asset.get().setSaleType(buyerType);
                    asset.get().setSaleStatus(true);
                    if (buyerType.equals("Employee")) {
                        Optional<Employees> emp = employeesRepository.findById(empId);
                        asset.get().setSoldPerson(emp.get().getName());
                        asset.get().setSoldEmployeeId(emp.get().getEmployeeId());
                    } else {

                        asset.get().setBuyer(buyer);
                    }
                    asset.get().setSaleDate(date);
                    asset.get().setLocation(location);
                    asset.get().setSubLocation(subLocation);
                    asset.get().setActualSaleValue(soldValue);
                    asset.get().setActive(false);
                    asset.get().setNote(note);

                    if (asset.get().isFinanceDetails()) {
                        AssetDetails assetDetails = asset.get().getAssetDetails();
                        DepreciationCalculatorBetween calculator = new DepreciationCalculatorBetween(
                                assetDetails.getCost(), assetDetails.getSalvageValue(),
                                assetDetails.getAssetLife(), LocalDate.parse(assetDetails.getPutToUseDate()),
                                LocalDate.parse(date));
                        double saleValue = 0.00;

                        if (assetDetails.getDepreciableMethod().equals("SLD")) {
                            saleValue = DayDepreciationCalculator.calculateStraightLine(
                                    assetDetails.getCost(),
                                    assetDetails.getSalvageValue(), assetDetails.getAssetLife(),
                                    LocalDate.parse(assetDetails.getPutToUseDate()), LocalDate.parse(date));
                        } else {
                            saleValue = DayDepreciationCalculator.calculateWDV(
                                    assetDetails.getCost(),
                                    assetDetails.getSalvageValue(), assetDetails.getAssetLife(),
                                    LocalDate.parse(assetDetails.getPutToUseDate()), LocalDate.parse(date));
                        }

                        asset.get().setSaleValue(saleValue);
                        Asset save = assetRepository.save(asset.get());

                        save.getAssetDepreciation().forEach(assetDepreciationRepository::delete);

                        List<DepreciationEntry> depreciationEntries;
                        if (assetDetails.getDepreciableMethod().equals("SLD")) {
                            depreciationEntries = calculator.calculateStraightLineDepreation();
                        } else {
                            depreciationEntries = calculator.calculateWDVDepreation();
                        }

                        depreciationEntries.forEach(dep -> {
                            AssetDepreciation ads = new AssetDepreciation();
                            ads.setYearCode(dep.getYearCode());
                            ads.setPeriod(dep.getPeriod());
                            ads.setStartdate(dep.getStartDate().toString());
                            ads.setEndDate(dep.getEndDate().toString());
                            ads.setBookValue(dep.getInitialValue());
                            ads.setDepreciation(dep.getDepreciation());
                            ads.setAccumulatedDepreciation(dep.getAccumulated());
                            ads.setEndingBookValue(dep.getFinalValue());
                            ads.setCompanyId(asset.get().getCompanyId());
                            ads.setSuperCompanyId(asset.get().getSuperCompanyId());
                            ads.setStatus(true);
                            ads.setAsset(save);
                            assetDepreciationRepository.save(ads);
                        });

                        return ResponseEntity.status(HttpStatus.CREATED).body("Asset Sold");
                    } else {
                        assetRepository.save(asset.get());
                        return ResponseEntity.status(HttpStatus.CREATED).body("Asset Sold");
                    }

                case "Scrap":
                    asset.get().setPreStatus(asset.get().getStatus());
                    asset.get().setStatus(type);
                    asset.get().setLocation(location);
                    asset.get().setSubLocation(subLocation);
                    asset.get().setActive(false);
                    asset.get().setNote(note);
                    assetRepository.save(asset.get());
                    return ResponseEntity.status(HttpStatus.CREATED).body("Asset Scraped");
                case "Stolen":
                    asset.get().setPreStatus(asset.get().getStatus());
                    asset.get().setStatus(type);
                    asset.get().setLocation(location);
                    asset.get().setSubLocation(subLocation);
                    asset.get().setActive(false);
                    asset.get().setNote(note);
                    assetRepository.save(asset.get());
                    return ResponseEntity.status(HttpStatus.CREATED).body("Reported");
                default:
                    return ResponseEntity.badRequest().body("Invalid Type");
            }
        } else {
            return ResponseEntity.badRequest().body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> updateBlukAssetScrap(List<Long> id, String type, String note, String location,
            String subLocation, String date) {

        id.forEach(x -> {
            Optional<Asset> asset = assetRepository.findById(x);
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus(type);
            asset.get().setLocation(location);
            asset.get().setSubLocation(subLocation);
            asset.get().setActive(false);
            asset.get().setNote(note);
            assetRepository.save(asset.get());

        });
        return ResponseEntity.status(HttpStatus.CREATED).body("Asset Updated");
    }

    public static String generateFiscalYearCode(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = sdf.parse(dateString);

        // Create a calendar instance and set the given date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);

        // Determine the fiscal year start date (e.g., April 1st of the current year)
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Check if the given date is before or after the fiscal year start date
        if (inputDate.before(calendar.getTime())) {
            // If before, consider the previous year as the start of fiscal year
            calendar.add(Calendar.YEAR, -1);
        }

        // Get the fiscal year start and end years
        int fiscalYearStartYear = calendar.get(Calendar.YEAR);
        int fiscalYearEndYear = fiscalYearStartYear + 1;

        // Format the fiscal year code as "YYYY-YY"
        String fiscalYearCode = fiscalYearStartYear + "-" + String.format("%02d", fiscalYearEndYear % 100);

        return fiscalYearCode;
    }

    @Override
    public ResponseEntity<?> getAssetIds() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assetCategoryRepository.getCategory(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> releaseReserveAsset(Long id) {
        Optional<Asset> asset = assetRepository.findById(id);
        if (asset.isPresent()) {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("Active");
            asset.get().setReserveDept(null);
            assetRepository.save(asset.get());
            return ResponseEntity.status(HttpStatus.OK).body("{\"Asset\": \"Released\"}");
        } else {
            return ResponseEntity.badRequest().body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> updateFinanceEntry(@Valid AssetDetails details) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findById(details.getTempAssetId());

        if (asset.isPresent()) {
            details.setAsset(asset.get());
            details.setCompanyId(user.getCompanyId());
            details.setSuperCompanyId(user.getSuperCompanyId());
            AssetDetails save = assetDetailsRepository.save(details);
            asset.get().setFinanceDetails(true);
            assetRepository.save(asset.get());

            if (save.isDepreciableAsset() && save.isPutToUseDateAvailability()) {
                LocalDate putToUseDate = LocalDate.parse(save.getPutToUseDate());
                List<DepreciationEntry> depreciatelist = new ArrayList<>();
                DepreciationCalculator calculator = new DepreciationCalculator(save.getCost(),
                        save.getSalvageValue(),
                        save.getAssetLife(), putToUseDate);
                if (save.getDepreciableMethod().equals("SLD")) {
                    depreciatelist = calculator.calculateStraightLineDepreation();
                } else {
                    depreciatelist = calculator.calculateWDVDepreation();
                }
                depreciatelist.forEach(dep -> {
                    AssetDepreciation ads = new AssetDepreciation();
                    ads.setYearCode(dep.getYearCode());
                    ads.setPeriod(dep.getPeriod());
                    ads.setStartdate(dep.getStartDate().toString());
                    ads.setEndDate(dep.getEndDate().toString());
                    ads.setBookValue(dep.getInitialValue());
                    ads.setDepreciation(dep.getDepreciation());
                    ads.setAccumulatedDepreciation(dep.getAccumulated());
                    ads.setEndingBookValue(dep.getFinalValue());
                    ads.setCompanyId(user.getCompanyId());
                    ads.setSuperCompanyId(user.getSuperCompanyId());
                    ads.setStatus(true);
                    ads.setAsset(asset.get());
                    assetDepreciationRepository.save(ads);
                });
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
        } else {
            return ResponseEntity.badRequest().body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> updateFinanceEntryEdit(@Valid AssetDetails details) {
        Optional<Asset> asset = assetRepository.findById(details.getTempAssetId());
        if (asset.isPresent()) {
            details.setAsset(asset.get());
            AssetDetails save = assetDetailsRepository.save(details);
            assetRepository.save(asset.get());

            return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
        } else {
            return ResponseEntity.badRequest().body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> updateAssetInsuranceAmc(AssetStatusDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findById(dto.getAssetId());
        if (asset.isPresent()) {
            switch (dto.type) {
                case "AMC":

                    AssetAmc amc = new AssetAmc();
                    amc.setCompanyId(user.getCompanyId());
                    amc.setSuperCompanyId(user.getSuperCompanyId());
                    amc.setAsset(asset.get());
                    amc.setStartDate(dto.getStartDate());
                    amc.setEndDate(dto.getEndDate());
                    amc.setSupplier(dto.getService());
                    amc.setStatus(true);
                    assetAmcRepository.save(amc);

                    asset.get().setAmcStatus(true);
                    assetRepository.save(asset.get());

                    return ResponseEntity.status(HttpStatus.CREATED).body("AMC Updated");
                case "INSURANCE":
                    AssetInsurance insurance = new AssetInsurance();

                    insurance.setCompanyId(user.getCompanyId());
                    insurance.setSuperCompanyId(user.getSuperCompanyId());
                    insurance.setAsset(asset.get());
                    insurance.setStartDate(dto.getStartDate());
                    insurance.setEndDate(dto.getEndDate());
                    insurance.setInsuranceCompany(dto.getInsuranceName());
                    insurance.setAmount(dto.getAmount());
                    insurance.setStatus(true);
                    assetInsuranceRepository.save(insurance);

                    asset.get().setInsuranceStatus(true);
                    assetRepository.save(asset.get());

                    return ResponseEntity.status(HttpStatus.CREATED).body("Insurance Updated");
                default:
                    return ResponseEntity.badRequest().body("Invalid Type");
            }
        } else {
            return ResponseEntity.badRequest().body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> getFinanceByAssetId(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<AssetDetails> detailsByAssetId = assetDetailsRepository.getDetailsByAssetId(id, user.getCompanyId(),
                user.getSuperCompanyId());
        if (detailsByAssetId.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(detailsByAssetId.get());
        } else {
            return ResponseEntity.badRequest().body("Asset Detail Not FOund");
        }
    }

    @Override
    public ResponseEntity<?> getAssetsLogisticsUpdate(Long id, String date) {
        Optional<Asset> findById = assetRepository.findById(id);
        if (findById.isPresent()) {
            findById.get().setStatus("Active");
            findById.get().setPreStatus("IN-TRANSIT");
            assetRepository.save(findById.get());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Asset Recived");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Asset Not Found");
        }

    }

    @Override
    public ResponseEntity<?> getAllAssetsLogisticsUpdate(List<Long> id, String date) {
        if (id.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Select from list");
        } else {
            id.forEach(x -> {
                Optional<Asset> findById = assetRepository.findById(x);
                if (findById.isPresent()) {
                    findById.get().setStatus("Active");
                    findById.get().setPreStatus("IN-TRANSIT");
                    assetRepository.save(findById.get());

                }
            });
            return ResponseEntity.status(HttpStatus.CREATED).body("Asset Recived");
        }

    }

    @Override
    public ResponseEntity<?> getAssetsLogisticsDismiss(Long id) {
        Optional<Asset> findById = assetRepository.findById(id);
        if (findById.isPresent()) {
            findById.get().setActive(false);
            findById.get().setDismiss(true);
            findById.get().setStatus("Dismiss");

            assetRepository.save(findById.get());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Asset Dismiss");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> getAllAssetsLogisticsDismiss(List<Long> id) {
        if (id.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Select from list");
        } else {

            id.forEach(x -> {
                Optional<Asset> findById = assetRepository.findById(x);
                if (findById.isPresent()) {
                    findById.get().setActive(false);
                    findById.get().setDismiss(true);
                    findById.get().setStatus("Dismiss");
                    assetRepository.save(findById.get());
                }
            });
            return ResponseEntity.status(HttpStatus.CREATED).body("Asset Dismiss");

        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public class PrintData {
        String tagId;
        String serial;
        String printValue;
    }

    @Override
    public ResponseEntity<?> getAssetTagIdForQRCODE(String type, List<Long> ids) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (type.equals("id")) {
            List<PrintData> tagId = new ArrayList<>();
            ids.forEach(x -> {
                Optional<Asset> tag = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(x, user.getCompanyId(),
                        user.getSuperCompanyId());
                PrintData data = new PrintData();
                data.setTagId(tag.get().getAssetTagId());
                data.setSerial(tag.get().getSerialNo());
                data.setPrintValue("CompanyId: " + tag.get().getCompanyId() +
                        "\n" +
                        "SuperCompanyId: " + tag.get().getSuperCompanyId() +
                        "\n" +
                        "Tag ID: " + tag.get().getAssetTagId());

                tagId.add(data);
            });
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(tagId);
        } else {
            List<PrintData> serials = new ArrayList<>();
            ids.forEach(x -> {
                Optional<Asset> tag = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(x, user.getCompanyId(),
                        user.getSuperCompanyId());
                PrintData data = new PrintData();
                data.setTagId(tag.get().getAssetTagId());
                data.setSerial(tag.get().getSerialNo());
                data.setPrintValue("SC:" + tag.get().getCompanyId() + ",C:" + tag.get().getSuperCompanyId() + ",SL:"
                        + tag.get().getSerialNo());
                serials.add(data);
            });

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(serials);

        }
    }

    @Override
    public ResponseEntity<?> updateAssetForProject(AssetForProjectDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (dto.isType()) {
            Optional<ProjectMaster> projectMaster = projectMasterRepository.findById(dto.getProjectId());
            dto.getAssetIds().forEach(x -> {
                Optional<Asset> asset = assetRepository.findById(x);
                asset.get().setStatus("Project");
                asset.get().setPreStatus("Active");
                assetRepository.save(asset.get());

                AssetProject project = new AssetProject();
                project.setStartDate(dto.getStartDate());
                project.setEndDate(dto.getEndDate());
                project.setProject(projectMaster.get().getName());
                project.setProjectLocation(projectMaster.get().getLocation());
                project.setProjectId(dto.getProjectId());
                project.setNote(dto.getNote());
                project.setSuperCompanyId(user.getSuperCompanyId());
                project.setCompanyId(user.getCompanyId());
                project.setStatus(true);
                project.setAsset(asset.get());
                assetProjectRepository.save(project);
            });
        } else {
            Optional<Asset> asset = assetRepository.findById(dto.getAssetId());
            asset.get().setStatus("Project");
            asset.get().setPreStatus("Active");
            assetRepository.save(asset.get());
            Optional<ProjectMaster> projectMaster = projectMasterRepository.findById(dto.getProjectId());
            AssetProject project = new AssetProject();
            project.setStartDate(dto.getStartDate());
            project.setEndDate(dto.getEndDate());
            project.setProject(projectMaster.get().getName());
            project.setProjectLocation(projectMaster.get().getLocation());
            project.setProjectId(dto.getProjectId());
            project.setNote(dto.getNote());
            project.setSuperCompanyId(user.getSuperCompanyId());
            project.setCompanyId(user.getCompanyId());
            project.setStatus(true);
            project.setAsset(asset.get());
            assetProjectRepository.save(project);
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Deployed");

    }

    @Override
    public ResponseEntity<?> updateAssetForProjectReturn(List<Long> id, String date) {

        id.forEach(x -> {
            Optional<Asset> asset = assetRepository.findById(x);
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("Active");

            asset.get().getAssetProject().forEach(y -> {
                if (y.isStatus()) {
                    y.setEndDate(date);
                    y.setStatus(false);
                }
            });
            assetRepository.save(asset.get());
        });
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Returned");
    }

    @Override
    public ResponseEntity<?> locationUpdate(LocationChangeDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(dto.assetId, user.getCompanyId(),
                user.getSuperCompanyId());
        if (asset.isPresent()) {
            asset.get().setLocation(dto.getLocationName());
            asset.get().setSubLocation(dto.getSubLocationName());
            assetRepository.save(asset.get());

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Location Updated to: " + dto.getLocationName());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> updateAssetRenting(Long id, String type, String note, String startDate, String endDate) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());

        if (asset.isPresent()) {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("Rent");
            assetRepository.save(asset.get());

            AssetRent rent = new AssetRent();
            rent.setCompanyId(user.getCompanyId());
            rent.setSuperCompanyId(user.getSuperCompanyId());
            rent.setStartDate(startDate);
            rent.setEndDate(endDate);
            rent.setNote(note);
            rent.setStatus(true);
            rent.setAsset(asset.get());

            assetRentRepository.save(rent);

            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Asset Rented");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Asset Not Found");
        }

    }

    @Override
    public ResponseEntity<?> assetReturn(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());

        if (asset.isPresent()) {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("Active");
            Asset save = assetRepository.save(asset.get());

            save.getAssetRent().forEach(x -> {

                if (x.isStatus()) {
                    x.setEndDate(LocalDate.now().toString());
                    x.setStatus(false);
                    assetRentRepository.save(x);
                }

            });
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Asset Returned");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> dismissAsset(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());
        if (asset.isPresent()) {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("DELETED");
            asset.get().setActive(false);
            asset.get().setDismiss(true);
            assetRepository.save(asset.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Asset Removed");

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Asset Not Found");
        }
    }

    @Override
    public ResponseEntity<?> updateSerial(Long id, String serial) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());

        List<Asset> serials = assetRepository.findBySerialNoAndCompanyIdAndSuperCompanyId(serial, user.getCompanyId(),
                user.getSuperCompanyId());

        if (serials.isEmpty()) {

            if (asset.isPresent()) {
                asset.get().setSerialNo(serial);
                assetRepository.save(asset.get());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Serial Data Updated");

            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Asset Not Found");

            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Serial Already in Database! Duplicate Entry");

        }

    }

    @Override
    public ResponseEntity<?> postCustomerAssign(AssetCustomerAssign dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(dto.getAssetId(),
                user.getCompanyId(),
                user.getSuperCompanyId());
        dto.setAsset(asset.get());
        dto.setCompanyId(user.getCompanyId());
        dto.setSuperCompanyId(user.getSuperCompanyId());
        if (dto.isOutTransit()) {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("Out-Transit");
        } else {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("MAPPED");

        }
        assetRepository.save(asset.get());
        assetCustomerAssignRepository.save(dto);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Asset Assign to The Customer");

    }

    @Override
    public ResponseEntity<?> assetCustomerWork(Long id, String type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(id,
                user.getCompanyId(),
                user.getSuperCompanyId());

        if (type.equals("RELEASE")) {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("Active");
            assetRepository.save(asset.get());
            asset.get().getAssetCustomerAssign().forEach(x ->{
                if(x.isActive()){
                    x.setEndDate(LocalDate.now().toString());
                    x.setActive(false);
                    x.setAsset(asset.get());
                    assetCustomerAssignRepository.save(x);
                }
            });
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Asset Release from The Customer");
        } else {
            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("MAPPED");
            assetRepository.save(asset.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Asset Reached to The Customer");
        }

    }

}
