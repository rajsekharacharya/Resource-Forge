package com.app.resourceforge.service.serviceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.resourceforge.configuration.UniqueCodeGenerator;
import com.app.resourceforge.model.ApplicationSetup;
import com.app.resourceforge.model.AssetType;
import com.app.resourceforge.model.AssetTypeDetail;
import com.app.resourceforge.model.Company;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.repository.ApplicationSetupRepository;
import com.app.resourceforge.repository.AssetTypeDetailRepository;
import com.app.resourceforge.repository.AssetTypeRepository;
import com.app.resourceforge.repository.CompanyRepository;
import com.app.resourceforge.repository.SubscriptionPlanRepository;
import com.app.resourceforge.repository.UserRepository;
import com.app.resourceforge.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final AssetTypeDetailRepository assetTypeDetailRepository;
    private final ApplicationSetupRepository applicationSetupRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
    public ResponseEntity<?> getAllCompanies() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(companyRepository.findBySuperCompanyId(user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getTotalCompany() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(companyRepository.findBySuperCompanyId(user.getSuperCompanyId()).size());
    }

    @Override
    public ResponseEntity<?> getCompanyById(Integer id) {
        return companyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> createCompany(@Valid Company company) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        Integer companyCount = companyRepository.countBySuperCompanyId(user.getSuperCompanyId());

        Optional<SubscriptionPlan> findByStatus = subscriptionPlanRepository.findByStatusAndSuperCompanyId(true,
                user.getSuperCompanyId());
        if (findByStatus.isPresent()) {
            if (findByStatus.get().getCompany() > companyCount) {
                company.setStatus(true);
                String randomCode;
                do {
                    randomCode = UniqueCodeGenerator.generateRandomCode(6);
                } while (companyRepository.findByCode(randomCode).isPresent());
                company.setCode(randomCode);
                company.setSuperCompanyId(user.getSuperCompanyId());
                Company createdCompany = companyRepository.save(company);
                List<ApplicationSetup> setup = Arrays.asList(
                        new ApplicationSetup("COMPANY SHORT NAME", "", true, false, false, user.getSuperCompanyId(),
                                createdCompany.getId()),
                        new ApplicationSetup("ASSET ID COUNT", "1", true, false, false, user.getSuperCompanyId(),
                                createdCompany.getId()));

                applicationSetupRepository.saveAll(setup);

                Map<Integer, String> data = new HashMap<>();
                data.put(1, "LAPTOP");
                data.put(2, "DESKTOP");
                data.put(3, "ALL-IN-ONE");

                for (int i = 1; i <= 3; i++) {
                    AssetType type = new AssetType();
                    type.setSuperCompanyId(createdCompany.getSuperCompanyId());
                    type.setCompanyId(createdCompany.getId());
                    type.setType(data.get(i));
                    type.setStatus(true);
                    AssetType save = assetTypeRepository.save(type);

                    List<AssetTypeDetail> detailList = Arrays.asList(
                            new AssetTypeDetail("PROCESSOR", "data01", "Text", true, save.getId(), save,
                                    save.getCompanyId(), save.getSuperCompanyId()),
                            new AssetTypeDetail("RAM", "data02", "Text", true, save.getId(), save, save.getCompanyId(),
                                    save.getSuperCompanyId()),
                            new AssetTypeDetail("SSD", "data03", "Text", false, save.getId(), save, save.getCompanyId(),
                                    save.getSuperCompanyId()),
                            new AssetTypeDetail("HDD", "data04", "Text", false, save.getId(), save, save.getCompanyId(),
                                    save.getSuperCompanyId()),
                            new AssetTypeDetail("GRAPHIC CARD", "data05", "Text", false, save.getId(), save,
                                    save.getCompanyId(), save.getSuperCompanyId()),
                            new AssetTypeDetail("VRAM", "data06", "Text", false, save.getId(), save,
                                    save.getCompanyId(), save.getSuperCompanyId()),
                            new AssetTypeDetail("OPERATING SYSTEM", "data07", "Text", false, save.getId(), save,
                                    save.getCompanyId(), save.getSuperCompanyId()),
                            new AssetTypeDetail("ANTI VIRUS", "data08", "Text", false, save.getId(), save,
                                    save.getCompanyId(), save.getSuperCompanyId()),
                            new AssetTypeDetail("MAC ADDRESS", "data09", "Text", false, save.getId(), save,
                                    save.getCompanyId(), save.getSuperCompanyId()),
                            new AssetTypeDetail("IP ADDRESS", "data10", "Text", false, save.getId(), save,
                                    save.getCompanyId(), save.getSuperCompanyId()));

                    assetTypeDetailRepository.saveAll(detailList);

                }

                return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
            } else {
                return ResponseEntity.status(HttpStatus.CREATED).body("Company Creation limit exceeded");
            }

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Active Plan.");
        }

    }

    @Override
    public ResponseEntity<?> updateCompany(@Valid Company company) {
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated");
    }

    @Override
    public ResponseEntity<?> deleteCompany(Integer id) {
        Optional<Company> companys = companyRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = companys.map(company -> {
            company.setStatus(!company.isStatus());
            Company update = companyRepository.save(company);
            if (update.isStatus()) {
                userRepository.findByCoId(update.getId()).forEach(user -> {
                    user.setAccountNotExpired(true);
                    userRepository.save(user);
                });
            } else {
                userRepository.findByCoId(update.getId()).forEach(user -> {
                    user.setAccountNotExpired(false);
                    userRepository.save(user);
                });
            }
            return messages.get(company.isStatus());
        }).orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
