package com.app.resourceforge.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

public interface ExcelService {

    ResponseEntity<?> getAssetTemplate(String type) throws IOException;
    ResponseEntity<?> getAssetFinanceTemplate() throws IOException;
    ResponseEntity<?> getEmployeesTemplate() throws IOException;
    ResponseEntity<?> getSuplierTemplate() throws IOException;
    ResponseEntity<?> getManual() throws IOException;

    
}
