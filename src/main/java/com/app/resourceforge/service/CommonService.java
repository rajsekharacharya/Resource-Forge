package com.app.resourceforge.service;

import org.springframework.http.ResponseEntity;

import com.app.resourceforge.DTO.FormData;

public interface CommonService {

    ResponseEntity<?> uploadExcelForEmp(FormData form);

    ResponseEntity<?> uploadExcelForAsset(FormData form);

    ResponseEntity<?> uploadExcelForSupplier(FormData form);

    ResponseEntity<?> uploadExcelForAssetFinance(FormData form);
    
}
