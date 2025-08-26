package com.app.resourceforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.service.CallLogService;

@RestController
@RequestMapping("/CallLog")
public class CallLogController {

    @Autowired
    CallLogService callLogService;

    @GetMapping(value = "/getAllCallLog")
    public ResponseEntity<?> getAllCallLog() {
        return callLogService.getAllCallLog();
    }
    @GetMapping(value = "/getCallLog")
    public ResponseEntity<?> getCallLog() {
        return callLogService.getCallLog();
    }

    @GetMapping(value = "/getCallLogForEngg")
    public ResponseEntity<?> getCallLogForEngg() {
        return callLogService.getCallLogForEngg();
    }

    @PutMapping(value = "/disMissCallLog")
    public ResponseEntity<?> disMissCallLog(@RequestParam  Long id) {
        return callLogService.disMissCallLog(id);
    }
    @PutMapping(value = "/serviceResolve")
    public ResponseEntity<?> serviceResolve(@RequestParam  Long id) {
        return callLogService.serviceResolve(id);
    }
    @PutMapping(value = "/serviceAcceleration")
    public ResponseEntity<?> serviceAcceleration(@RequestParam  Long id,@RequestParam String reason) {
        return callLogService.serviceAcceleration(id,reason);
    }

    @PutMapping(value = "/logAssign")
    public ResponseEntity<?> logAssign(@RequestParam Long id,@RequestParam String priority,@RequestParam String engineerName,@RequestParam Integer engineer ) {
        return callLogService.logAssign(id,priority,engineer,engineerName);
    }

}
