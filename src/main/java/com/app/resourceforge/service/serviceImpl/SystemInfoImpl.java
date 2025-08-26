package com.app.resourceforge.service.serviceImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.AssetVersion;
import com.app.resourceforge.model.Company;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.SystemInfo;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.repository.AssetVersionRepository;
import com.app.resourceforge.repository.CompanyRepository;
import com.app.resourceforge.repository.SystemInfoRepository;
import com.app.resourceforge.service.SystemInfoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SystemInfoImpl implements SystemInfoService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final SystemInfoRepository repository;
    private final AssetRepository assetRepository;
    private final CompanyRepository companyRepository;
    private final AssetVersionRepository assetVersionRepository;

    @Override
    @Transactional
    public ResponseEntity<?> saveSystemInfo(SystemInfo systemInfo) {

        Optional<Company> companyData = companyRepository.findByCode(systemInfo.getCompanyCode());
        if (companyData.isPresent()) {

            boolean changesDetected = false;
            StringBuilder changeLog = new StringBuilder("Changes detected:\n");

            Optional<SystemInfo> existingOpt = repository.findByCompanyCodeAndAnyIdentifier(systemInfo.getCompanyCode(),
                    systemInfo.getUuid(), systemInfo.getMacAddress(), systemInfo.getSerialNumber());

            if (existingOpt.isPresent()) {

                SystemInfo existing = existingOpt.get();
                Field[] fields = SystemInfo.class.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    // Skip collections
                    if (field.getName().equals("installedSoftware") || field.getName().equals("bios")
                            || field.getName().equals("id") || field.getName().equals("availableRam")) {
                        continue;
                    }
                    try {
                        Object oldValue = field.get(existing);
                        Object newValue = field.get(systemInfo);

                        if (!Objects.equals(oldValue, newValue)) {
                            changeLog.append(field.getName())
                                    .append(" changed from ")
                                    .append(oldValue)
                                    .append(" to ")
                                    .append(newValue)
                                    .append("\n");
                            changesDetected = true;
                        }

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                systemInfo.setId(existing.getId());
            }

            SystemInfo save = repository.save(systemInfo);

            Company company = companyData.get();
            Optional<Asset> assetData = assetRepository.findByCompanyCodeAndAnyIdentifier(
                    save.getUuid(), save.getSerialNumber(), save.getMacAddress(),
                    company.getId(), company.getSuperCompanyId());
            if (assetData.isPresent()) {
                Asset asset = assetData.get();
                asset.setModel(systemInfo.getModel());
                asset.setType(save.getAssetType());
                asset.setData01(save.getProcessor());
                asset.setData02(save.getRam());
                asset.setData03(save.getSsdTotal());
                asset.setData04(save.getHddTotal());
                asset.setData05(save.getGraphicsCard());
                asset.setData06(save.getGraphicsCardVRam());
                asset.setData07(save.getOs());
                asset.setData08(save.getAntiVirus());
                asset.setData09(save.getMacAddress());
                asset.setData10(save.getIpAddress());
                asset.setUuid(save.getUuid());
                asset.setDeviceName(save.getDeviceName());
                if (asset.getSerialNo().equals("Default string")) {
                    asset.setSerialNo(save.getSerialNumber());
                }
                asset.setManufacturer(save.getManufacturer());
                asset.setSystemApplication(save.getId());
                Asset save2 = assetRepository.save(asset);

                if (changesDetected) {
                    AssetVersion version = new AssetVersion();
                    version.setLog(changeLog.toString());
                    List<AssetVersion> versionList = save2.getVersion();
                    versionList.add(version);
                    versionList.forEach(x -> {
                        x.setAsset(save2);
                    });
                    assetVersionRepository.saveAll(versionList);
                }

            } else {
                Asset asset = new Asset();
                asset.setCompanyId(company.getId());
                asset.setSuperCompanyId(company.getSuperCompanyId());
                asset.setModel(systemInfo.getModel());
                asset.setType(save.getAssetType());
                asset.setData01(save.getProcessor());
                asset.setData02(save.getRam());
                asset.setData03(save.getSsdTotal());
                asset.setData04(save.getHddTotal());
                asset.setData05(save.getGraphicsCard());
                asset.setData06(save.getGraphicsCardVRam());
                asset.setData07(save.getOs());
                asset.setData08(save.getAntiVirus());
                asset.setData09(save.getMacAddress());
                asset.setData10(save.getIpAddress());
                asset.setDeviceName(save.getDeviceName());
                asset.setUuid(save.getUuid());
                asset.setSerialNo(save.getSerialNumber());
                asset.setManufacturer(save.getManufacturer());
                asset.setSystemApplication(save.getId());
                asset.setStatus("Pre-Entry");
                asset.setPreStatus("Pre-Entry");
                assetRepository.save(asset);

            }

            return ResponseEntity.status(HttpStatus.OK).body("System information saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company code not found");
        }
    }

    @Override
    public ResponseEntity<Resource> downloadRenamedFile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        Path uploadDirPath = Paths.get(uploadDir, "client/SystemInfoCollector.exe");
        File file = uploadDirPath.toFile();

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Optional<Company> companyData = companyRepository.findById(user.getCompanyId());
        // Wrap file as Spring resource
        Resource resource = new FileSystemResource(file);
        String code = companyData.get().getCode();
        // Force download with new name
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + code + ".exe\"")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

}
