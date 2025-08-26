package com.app.resourceforge.autoService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.app.resourceforge.repository.AssetAmcRepository;
import com.app.resourceforge.repository.AssetInsuranceRepository;
import com.app.resourceforge.repository.AssetRepository;

@Service
public class AssetAutoService {

    @Autowired
    AssetRepository assetRepository;
    @Autowired
    AssetAmcRepository assetAmcRepository;
    @Autowired
    AssetInsuranceRepository assetInsuranceRepository;

    @Scheduled(cron = "0 0 1 * * ?")
    public void Warranty() {
        assetAmcRepository.getExpiredWarranty(LocalDate.now().toString()).forEach(x -> {
            x.setWarrantyStatus(false);
            assetRepository.save(x);
        });
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void AMC() {
        assetAmcRepository.getExpiredAmc(LocalDate.now().toString()).forEach(x -> {
            x.setStatus(false);
            x.getAsset().setAmcStatus(false);
            assetAmcRepository.save(x);
        });
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void Insurance() {
        assetAmcRepository.getExpiredInsurance(LocalDate.now().toString()).forEach(x -> {
            x.setStatus(false);
            x.getAsset().setInsuranceStatus(false);
            assetInsuranceRepository.save(x);
        });
    }
}
