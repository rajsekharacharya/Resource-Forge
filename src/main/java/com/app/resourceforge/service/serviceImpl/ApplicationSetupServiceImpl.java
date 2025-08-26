package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.ApplicationSetup;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.ApplicationSetupRepository;
import com.app.resourceforge.service.ApplicationSetupService;

@Service
public class ApplicationSetupServiceImpl implements ApplicationSetupService {

    @Autowired
    ApplicationSetupRepository applicationSetupRepository;

    @Override
    public ResponseEntity<?> getAllApplicationSetups() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(applicationSetupRepository.findBySuperCompanyIdAndCompanyId(user.getSuperCompanyId(),
                user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getApplicationSetupById(Integer id) {
        return ResponseEntity.ok(applicationSetupRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createApplicationSetup(ApplicationSetup applicationSetup) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        applicationSetup.setStatus(true);
        applicationSetup.setSuperCompanyId(user.getSuperCompanyId());
        applicationSetup.setCompanyId(user.getCompanyId());
        applicationSetupRepository.save(applicationSetup);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> updateApplicationSetup(ApplicationSetup applicationSetup) {
        applicationSetupRepository.save(applicationSetup);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> deleteApplicationSetup(Integer id) {

        Optional<ApplicationSetup> applicationSetup = applicationSetupRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = applicationSetup
                .map(setup -> {
                    setup.setStatus(!setup.isStatus());
                    applicationSetupRepository.save(setup);
                    return messages.get(setup.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);

    }

}
