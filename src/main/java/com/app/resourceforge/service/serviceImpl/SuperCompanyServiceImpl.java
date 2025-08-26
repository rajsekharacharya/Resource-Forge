package com.app.resourceforge.service.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.configuration.UniqueCodeGenerator;
import com.app.resourceforge.model.ApplicationSetup;
import com.app.resourceforge.model.AssetType;
import com.app.resourceforge.model.AssetTypeDetail;
import com.app.resourceforge.model.Company;
import com.app.resourceforge.model.Settings;
import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.model.SubscriptionPlanMaster;
import com.app.resourceforge.model.SuperCompany;
import com.app.resourceforge.model.User;
import com.app.resourceforge.repository.ApplicationSetupRepository;
import com.app.resourceforge.repository.AssetTypeDetailRepository;
import com.app.resourceforge.repository.AssetTypeRepository;
import com.app.resourceforge.repository.CompanyRepository;
import com.app.resourceforge.repository.SettingsRepository;
import com.app.resourceforge.repository.SubscriptionPlanMasterRepository;
import com.app.resourceforge.repository.SubscriptionPlanRepository;
import com.app.resourceforge.repository.SuperCompanyRepository;
import com.app.resourceforge.repository.UserRepository;
import com.app.resourceforge.service.SuperCompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuperCompanyServiceImpl implements SuperCompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final AssetTypeRepository assetTypeRepository;
    private final AssetTypeDetailRepository assetTypeDetailRepository;
    private final ApplicationSetupRepository applicationSetupRepository;
    private final SuperCompanyRepository superCompanyRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionPlanMasterRepository subscriptionPlanMasterRepository;
    private final SettingsRepository settingsRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> getAllSuperCompanies() {
        return ResponseEntity.ok(superCompanyRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getSuperCompanies() {
        return ResponseEntity.ok(superCompanyRepository.findByStatus(true));
    }

    @Override
    public ResponseEntity<?> getTotalSuperCompany() {
        return ResponseEntity.ok(superCompanyRepository.findAll().size());
    }

    @Override
    public ResponseEntity<?> getSuperCompanyById(Integer id) {
        return superCompanyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> createSuperCompany(@Valid SuperCompany superCompany) {
        String shortName = superCompany.getShortName();
        superCompany.setStatus(true);
        SuperCompany com = superCompanyRepository.save(superCompany);
        List<Settings> setting = Arrays.asList(
                new Settings(null, "COMPANY", 1, com.getId()),
                new Settings(null, "YEARCODE", 2, com.getId()),
                new Settings(null, "ASSET", 3, com.getId()),
                new Settings(null, "ID", 4, com.getId()));
        setting.forEach(x -> settingsRepository.save(x));

        List<SubscriptionPlanMaster> subscriptionPlanMaster = subscriptionPlanMasterRepository.findAll();
        List<SubscriptionPlan> companyPlan = new ArrayList<>();
        for (SubscriptionPlanMaster planMaster : subscriptionPlanMaster) {
            SubscriptionPlan plan = new SubscriptionPlan();
            plan.setSubsctptionMasterPlanId(planMaster.getId());
            plan.setPlanName(planMaster.getPlanName());
            plan.setAsset(planMaster.getAsset());
            plan.setCompany(planMaster.getCompany());
            plan.setSuperAdmin(planMaster.getSuperAdmin());
            plan.setUser(planMaster.getUser());
            plan.setPrice(planMaster.getPrice());
            plan.setValidity(planMaster.getValidity());
            plan.setMobileAccess(planMaster.isMobileAccess());
            plan.setDocUpload(planMaster.isDocUpload());
            plan.setNote(planMaster.getNote());
            plan.setSuperCompanyId(com.getId());
            plan.setStatus(false);
            plan.setPlanRequest(false);
            plan.setEditable(false);
            companyPlan.add(plan);
        }
        SubscriptionPlan plan = new SubscriptionPlan();
        plan.setSubsctptionMasterPlanId(0);
        plan.setPlanName("Custom");
        plan.setAsset(10000);
        plan.setCompany(10);
        plan.setSuperAdmin(10);
        plan.setUser(100);
        plan.setPrice(0.0);
        plan.setValidity(1);
        plan.setMobileAccess(true);
        plan.setDocUpload(true);
        plan.setNote("Custom");
        plan.setSuperCompanyId(com.getId());
        plan.setStatus(true);
        plan.setPlanRequest(false);
        plan.setEditable(true);
        plan.setStartDate(LocalDate.now());
        plan.setEndDate(LocalDate.now().plusYears(plan.getValidity()));
        companyPlan.add(plan);
        companyPlan.forEach(plans -> subscriptionPlanRepository.save(plans));

        Company company = new Company();
        company.setName(com.getName());
        company.setAddress(com.getAddress());
        company.setEmail(com.getEmail());
        company.setSuperCompanyId(com.getId());
        company.setStatus(true);
        String randomCode;
        do {
            randomCode = UniqueCodeGenerator.generateRandomCode(6);
        } while (companyRepository.findByCode(randomCode).isPresent());
        company.setCode(randomCode);
        Company sub = createCompany(company);
        creatSuperAdmin(shortName, com.getId());
        creatAdmin(shortName, com.getId(), sub.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> updateSuperCompany(@Valid SuperCompany company) {
        superCompanyRepository.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleSuperCompany(Integer id) {
        Optional<SuperCompany> companys = superCompanyRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = companys
                .map(company -> {
                    company.setStatus(!company.isStatus());
                    SuperCompany update = superCompanyRepository.save(company);
                    if (update.isStatus()) {
                        userRepository.findByRoleAndSuperCompanyId("SUPERADMIN", update.getId()).forEach(user -> {
                            user.setAccountNotExpired(true);
                            user.setEnabled(true);
                            userRepository.save(user);
                        });
                    } else {
                        userRepository.findByRoleAndSuperCompanyId("SUPERADMIN", update.getId()).forEach(user -> {
                            user.setAccountNotExpired(false);
                            user.setEnabled(false);
                            userRepository.save(user);
                        });

                        subscriptionPlanRepository.findByStatusAndSuperCompanyId(true, update.getId())
                                .ifPresent(plan -> {
                                    plan.setStatus(false);
                                    plan.setStartDate(null);
                                    plan.setEndDate(null);
                                    subscriptionPlanRepository.save(plan);
                                });
                    }
                    return messages.get(company.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

    private Company createCompany(Company company) {
        Company createdCompany = companyRepository.save(company);
        List<ApplicationSetup> setup = Arrays.asList(
                new ApplicationSetup("COMPANY SHORT NAME", "", true, false, false, createdCompany.getSuperCompanyId(),
                        createdCompany.getId()),
                new ApplicationSetup("ASSET ID COUNT", "1", true, false, false, createdCompany.getSuperCompanyId(),
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

        return createdCompany;

    }

    private void creatAdmin(String shortName, Integer superCompanyId, Integer companyId) {

        User user = new User();
        user.setName("Admin");
        user.setUsername(shortName + "_admin");
        user.setPassword(passwordEncoder.encode(shortName + "_admin"));
        user.setEmail(shortName + "_admin@test.com");
        user.setMobile("1234567890");
        user.setRole("ADMIN");
        user.setSuperCompanyId(superCompanyId);
        user.setCoId(companyId);
        user.setEnabled(true);
        user.setAccountNotExpired(true);
        userRepository.save(user);
    }

    private void creatSuperAdmin(String shortName, Integer superCompanyId) {
        User user = new User();
        user.setName("Super Admin");
        user.setUsername(shortName + "_superadmin");
        user.setPassword(passwordEncoder.encode(shortName + "_superadmin"));
        user.setEmail(shortName + "_superadminadmin@test.com");
        user.setMobile("1234567890");
        user.setRole("SUPERADMIN");
        user.setSuperCompanyId(superCompanyId);
        user.setCoId(0);
        user.setEnabled(true);
        user.setAccountNotExpired(true);
        userRepository.save(user);

    }
}
