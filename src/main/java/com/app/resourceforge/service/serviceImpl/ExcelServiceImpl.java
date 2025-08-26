package com.app.resourceforge.service.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.repository.AssetTypeRepository;
import com.app.resourceforge.service.ExcelService;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AssetTypeRepository assetTypeRepository;

    @Override
    public ResponseEntity<?> getAssetTemplate(String type) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();

        // Create Excel Workbook and Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("asset");

        List<String> columnList = new ArrayList<>();

        // Add the initial columns
        columnList.addAll(Arrays.asList("TagId","Model", "SerialNo.", "Manufacturer", "Supplier"));

        // Add all properties from the repository
        List<String> allProperties = assetTypeRepository.getAllProperties(type, user.getCompanyId(),
                user.getSuperCompanyId());
        columnList.addAll(allProperties);

        // Add the final columns
        columnList.addAll(Arrays.asList("Cost", "PurchaseNumber","PurchaseDate", "InvoiceNumber", "InvoiceDate", "WarrantyStatus",
                "StartDate", "EndDays"));

        // Convert the list to an array
        String[] allColumns = columnList.toArray(new String[0]);

        // Create Header Row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < allColumns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(allColumns[i]);
        }

        // Prepare Excel File
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Set Response Headers for Excel File
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + type + "_asset.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAssetFinanceTemplate() throws IOException {

        // Create Excel Workbook and Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Finance");

        List<String> columnList = new ArrayList<>();

        // Add the initial columns
        columnList.addAll(Arrays.asList("Asset Tag Id", "Asset Category ID", "Asset Sub Category ID", "Depreciation",
                "PutToUse Date", "Depreciable Method(SLD/WVD)", "Asset life"));

        // Create Header Row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnList.get(i));
        }

        // Prepare Excel File
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Set Response Headers for Excel File
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=finance_asset.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getEmployeesTemplate() throws IOException {
        // Create Excel Workbook and Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Finance");

        List<String> columnList = new ArrayList<>();

        // Add the initial columns
        columnList.addAll(Arrays.asList("Name", "EmployeeId", "Email", "Phone",
                "Department","Image"));

        // Create Header Row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnList.get(i));
        }

        // Prepare Excel File
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Set Response Headers for Excel File
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_template.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getSuplierTemplate() throws IOException{
        // Create Excel Workbook and Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Finance");

        List<String> columnList = new ArrayList<>();

        // Add the initial columns
        columnList.addAll(Arrays.asList("Type(supplier/service)", "Name"));

        // Create Header Row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columnList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnList.get(i));
        }

        // Prepare Excel File
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Set Response Headers for Excel File
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=suplier_template.xlsx");

        return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getManual() throws IOException {
        File file = new File("C:/upload/asset-management/test.pdf");
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        } else {
            Resource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        }
    }

}



