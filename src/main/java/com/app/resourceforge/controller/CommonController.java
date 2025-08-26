package com.app.resourceforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.resourceforge.DTO.FormData;
import com.app.resourceforge.service.CommonService;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    CommonService commonService;

    @PostMapping("/uploadExcelForEmp")
    public ResponseEntity<?> uploadExcelForEmp(@ModelAttribute FormData form) throws Exception {
        return commonService.uploadExcelForEmp(form);
    }
    @PostMapping("/uploadExcelForAsset")
    public ResponseEntity<?> uploadExcelForAsset(@ModelAttribute FormData form) throws Exception {
        return commonService.uploadExcelForAsset(form);
    }
    @PostMapping("/uploadExcelForAssetFinance")
    public ResponseEntity<?> uploadExcelForAssetFinance(@ModelAttribute FormData form) throws Exception {
        return commonService.uploadExcelForAssetFinance(form);
    }
    @PostMapping("/uploadExcelForSupplier")
    public ResponseEntity<?> uploadExcelForSupplier(@ModelAttribute FormData form) throws Exception {
        return commonService.uploadExcelForSupplier(form);
    }

}
