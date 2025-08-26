package com.app.resourceforge.service.serviceImpl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.CallLog;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.repository.CallLogRepository;
import com.app.resourceforge.service.CallLogService;

@Service
public class CallLogServiceImpl implements CallLogService {

    @Autowired
    CallLogRepository callLogRepository;

    @Autowired
    AssetRepository assetRepository;

    @Override
    public ResponseEntity<?> getAllCallLog() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(callLogRepository.getAllLog(user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getCallLog() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(callLogRepository.getActiveLog(user.getCompanyId(), user.getSuperCompanyId(),true));
    }

    @Override
    public ResponseEntity<?> disMissCallLog(Long id) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CallLog> issue = callLogRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());
        if (issue.isPresent()) {
            issue.get().setActive(false);
            issue.get().setStatus("DISMISS");
            issue.get().setDismissBy(user.getUser().getId());
            issue.get().setDismissByName(user.getUser().getName());
            issue.get().setResolvedByName(null);
            issue.get().setResolvedBy(null);
            issue.get().setAssignToName(null);
            issue.get().setAssignTo(null);
            callLogRepository.save(issue.get());

            Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(issue.get().getAssetID(),
                    user.getCompanyId(), user.getSuperCompanyId());

            if (asset.get().getStatus().equals("Maintenance")) {
                asset.get().setStatus(asset.get().getPreStatus());
                asset.get().setPreStatus("Maintenance");
                assetRepository.save(asset.get());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Dismissed");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System Error");
    }

    @Override
    public ResponseEntity<?> logAssign(Long id, String priority, Integer engineer, String engineerName) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CallLog> log = callLogRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());
        if (log.isPresent()) {
            log.get().setStatus("ASSIGN");
            log.get().setAssignTo(engineer);
            log.get().setAssignToName(engineerName);
            log.get().setAssignDate(LocalDate.now().toString());
            log.get().setPriority(priority);
            callLogRepository.save(log.get());

            Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(log.get().getAssetID(),
                    user.getCompanyId(), user.getSuperCompanyId());

            asset.get().setPreStatus(asset.get().getStatus());
            asset.get().setStatus("Maintenance");

            assetRepository.save(asset.get());

            return ResponseEntity.status(HttpStatus.CREATED).body("Assigned to" + engineerName);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System Error");
        }

    }

    @Override
    public ResponseEntity<?> getCallLogForEngg() {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getUser().getId());
        System.out.println(user.getCompanyId());
        System.out.println(user.getSuperCompanyId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(callLogRepository.getgetCallLogForEnggLog(user.getUser().getId(), user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> serviceResolve(Long id) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CallLog> log = callLogRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());
        if (log.isPresent()) {
            log.get().setStatus("RESOLVED");
            log.get().setActive(false);
            log.get().setResolvedByName(log.get().getAssignToName());
            log.get().setResolvedBy(log.get().getAssignTo());
            log.get().setResolvedDate(LocalDate.now().toString());
            callLogRepository.save(log.get());

            Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(log.get().getAssetID(),
                    user.getCompanyId(), user.getSuperCompanyId());

            asset.get().setStatus(asset.get().getPreStatus());
            asset.get().setPreStatus("Maintenance");

            assetRepository.save(asset.get());

            return ResponseEntity.status(HttpStatus.CREATED).body("Resolved");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System Error");
        }
    }

    @Override
    public ResponseEntity<?> serviceAcceleration(Long id, String reason) {
        MyUserDetails user = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<CallLog> log = callLogRepository.findByIdAndCompanyIdAndSuperCompanyId(id, user.getCompanyId(),
                user.getSuperCompanyId());
        if (log.isPresent()) {
            log.get().setStatus("PENDING");
            log.get().setReasonFromName(log.get().getAssignToName());
            log.get().setReasonFrom(log.get().getAssignTo());
            log.get().setAssignTo(null);
            log.get().setAssignToName(null);
            log.get().setReason(reason);
            callLogRepository.save(log.get());

            Optional<Asset> asset = assetRepository.findByIdAndCompanyIdAndSuperCompanyId(log.get().getAssetID(),
                    user.getCompanyId(), user.getSuperCompanyId());

            asset.get().setStatus(asset.get().getPreStatus());
            asset.get().setPreStatus("Maintenance");

            assetRepository.save(asset.get());

            return ResponseEntity.status(HttpStatus.CREATED).body("Acceleration");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System Error");
        }
    }



}
