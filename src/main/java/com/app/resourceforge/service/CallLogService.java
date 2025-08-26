package com.app.resourceforge.service;

import org.springframework.http.ResponseEntity;

public interface CallLogService {

    ResponseEntity<?> getCallLog();

    ResponseEntity<?> disMissCallLog(Long id);

    ResponseEntity<?> logAssign(Long id, String priority, Integer engineer, String engineerName);

    ResponseEntity<?> getCallLogForEngg();

    ResponseEntity<?> serviceResolve(Long id);

    ResponseEntity<?> serviceAcceleration(Long id, String reason);

    ResponseEntity<?> getAllCallLog();
    
}
