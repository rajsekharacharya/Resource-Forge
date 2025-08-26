package com.app.resourceforge.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.service.ExcelService;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    ExcelService excelService;

    @GetMapping(value = "/getManual")
    public ResponseEntity<?> getManual() throws IOException {
        return excelService.getManual();
    }
    @GetMapping(value = "/getAssetTemplate")
    public ResponseEntity<?> getAssetTemplate(@RequestParam String type) throws IOException {
        return excelService.getAssetTemplate(type);
    }
    @GetMapping(value = "/getAssetFinanceTemplate")
    public ResponseEntity<?> getAssetFinanceTemplate() throws IOException {
        return excelService.getAssetFinanceTemplate();
    }

        @GetMapping(value = "/getEmployeesTemplate")
    public ResponseEntity<?> getEmployeesTemplate() throws IOException {
        return excelService.getEmployeesTemplate();
    }

        @GetMapping(value = "/getSuplierTemplate")
    public ResponseEntity<?> getSuplierTemplate() throws IOException {
        return excelService.getSuplierTemplate();
    }

}
