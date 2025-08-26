package com.app.resourceforge.service.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.DTO.FormData;
import com.app.resourceforge.model.ApplicationSetup;
import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.AssetDetails;
import com.app.resourceforge.model.Employees;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.SubscriptionPlan;
import com.app.resourceforge.model.Supplier;
import com.app.resourceforge.model.DTO.AssetTypeDTO;
import com.app.resourceforge.repository.ApplicationSetupRepository;
import com.app.resourceforge.repository.AssetCategoryRepository;
import com.app.resourceforge.repository.AssetDetailsRepository;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.repository.AssetTypeRepository;
import com.app.resourceforge.repository.DepartmentRepository;
import com.app.resourceforge.repository.EmployeesRepository;
import com.app.resourceforge.repository.ManufacturerRepository;
import com.app.resourceforge.repository.SubscriptionPlanRepository;
import com.app.resourceforge.repository.SupplierRepository;
import com.app.resourceforge.service.CommonService;

@Service
public class CommonServiceImpl implements CommonService {

    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    ManufacturerRepository manufacturerRepository;
    @Autowired
    AssetTypeRepository assetTypeRepository;
    @Autowired
    AssetRepository assetRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ApplicationSetupRepository applicationSetupRepository;
    @Autowired
    SubscriptionPlanRepository subscriptionPlanRepository;
    @Autowired
    AssetCategoryRepository assetCategoryRepository;
    @Autowired
    AssetDetailsRepository assetDetailsRepository;

    @Override
    public ResponseEntity<?> uploadExcelForEmp(FormData form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (form.getFile().isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        } else {
            try (InputStream inputStream = form.getFile().getInputStream()) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                System.out.println("++++++coloum" + sheet.getRow(0).getLastCellNum());
                if (sheet.getRow(0).getLastCellNum() < 5) {
                    return new ResponseEntity<>("Invalid Excel format: Expected Minimum 5 columns",
                            HttpStatus.BAD_REQUEST);
                }
                List<Employees> emps = new ArrayList<>();
                List<String> errorDate = new ArrayList<>();

                Map<Integer, String> imagePaths = new HashMap<>();

                if (workbook instanceof XSSFWorkbook) {
                    extractImages((XSSFWorkbook) workbook, imagePaths, "employee");
                }

                sheet.iterator().forEachRemaining(row -> {
                    Employees emp = new Employees();
                    emp.setCompanyId(user.getCompanyId());
                    emp.setSuperCompanyId(user.getSuperCompanyId());
                    emp.setStatus(true);

                    if (row == null || row.getRowNum() == 0 || row.getCell(1) == null) {
                        return;
                    }

                    String name = getStringCellValue(row.getCell(0));
                    String employeeId = getStringCellValue(row.getCell(1));
                    String email = getStringCellValue(row.getCell(2));
                    String phone = getStringCellValue(row.getCell(3));
                    String department = getStringCellValue(row.getCell(4));

                    // System.out.println("++++++name" + name);
                    // System.out.println("++++++employeeId" + employeeId);
                    // System.out.println("++++++email" + email);
                    // System.out.println("++++++phone" + phone);
                    // System.out.println("++++++department" + department);
                    // // System.out.println("++++++imagePaths" + imagePaths);
                    // System.out.println("++++++row" + row.getRowNum());
                    // System.out.println("++++++rowImape" + imagePaths.get(row.getRowNum()));

                    boolean empId = employeesRepository.existsByEmployeeIdAndCompanyIdAndSuperCompanyId(employeeId,
                            user.getCompanyId(), user.getSuperCompanyId());

                    boolean depStatus = departmentRepository.existsByNameAndCompanyIdAndSuperCompanyId(department,
                            user.getCompanyId(), user.getSuperCompanyId());

                    if (!empId && depStatus) {
                        emp.setName(name);
                        emp.setEmployeeId(employeeId);
                        emp.setEmail(email);
                        emp.setPhone(phone);
                        emp.setDepartment(department);
                        emp.setImageLink(imagePaths.get(row.getRowNum()));

                        emps.add(emp);
                    } else {
                        errorDate.add(employeeId);
                    }
                });

                emps.forEach(employeesRepository::save);

                if (errorDate.isEmpty()) {
                    return new ResponseEntity<>("Data uploaded successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Data uploaded But Some Error Found on EmployeeId :"
                            + String.join(", ", errorDate), HttpStatus.OK);
                }

            } catch (IOException e) {
                return new ResponseEntity<>("Failed to upload data: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<?> uploadExcelForAsset(FormData form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (form.getFile().isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        } else {
            try (InputStream inputStream = form.getFile().getInputStream()) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                List<String> errorDate = new ArrayList<>();

                Optional<ApplicationSetup> serialKey = applicationSetupRepository
                        .findByKeyAndStatusAndSuperCompanyIdAndCompanyId("COMPANY SHORT NAME", true,
                                user.getSuperCompanyId(), user.getCompanyId());

                Optional<SubscriptionPlan> subscription = subscriptionPlanRepository.findByStatusAndSuperCompanyId(true,
                        user.getSuperCompanyId());

                Integer assetCount = assetRepository.countBySuperCompanyId(user.getSuperCompanyId());
                if (serialKey.isPresent() && !serialKey.get().getValue().isEmpty()) {

                    if (subscription.isPresent()
                            && subscription.get().getAsset() >= (assetCount + sheet.getLastRowNum() - 1)) {

                        sheet.iterator().forEachRemaining(row -> {
                            if (row == null || row.getRowNum() == 0 || row.getCell(1) == null) {
                                return;
                            }

                            System.out.println("++++++++++++++" + getStringCellValue(row.getCell(1)));

                            String assetTagId = null;
                            boolean assetTagStatus = false;
                            String type = form.getType();
                            String tag = getStringCellValue(row.getCell(0));
                            String model = getStringCellValue(row.getCell(1));
                            String serialNo = getStringCellValue(row.getCell(2));
                            String manufacturer = getStringCellValue(row.getCell(3));
                            String supplier = getStringCellValue(row.getCell(4));

                            boolean isSerial = assetRepository.existsBySerialNoAndCompanyIdAndSuperCompanyId(serialNo,
                                    user.getCompanyId(), user.getSuperCompanyId());

                            if (!isSerial) {

                                List<AssetTypeDTO> propertiesDetails = assetTypeRepository.getPropertiesDetails(type,
                                        user.getCompanyId(), user.getSuperCompanyId());
                                Map<String, String> rowValues = new HashMap<>();
                                // Iterate through the list to find matching header names

                                for (int i = 5; i < (5 + propertiesDetails.size()); i++) {
                                    String headerName = getStringCellValue(sheet.getRow(0).getCell(i));
                                    final int index = i;
                                    propertiesDetails.forEach(x -> {
                                        if (x.getCaption().equals(headerName)) {
                                            rowValues.put(x.getModel(), getStringCellValue(row.getCell(index)));
                                        }
                                    });
                                }
                                Optional<ApplicationSetup> assetId = applicationSetupRepository
                                        .findByKeyAndStatusAndSuperCompanyIdAndCompanyId("ASSET ID COUNT", true,
                                                user.getSuperCompanyId(), user.getCompanyId());

                                if (tag == null || tag.isEmpty() || tag.isBlank()) {
                                    String fiscalYearCode = null;
                                    try {
                                        fiscalYearCode = generateFiscalYearCode(LocalDate.now().toString());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    String tagId = serialKey.get().getValue() + "/";
                                    if (fiscalYearCode != null) {
                                        tagId += fiscalYearCode + "/";
                                    }
                                    tagId += type + "/";
                                    tagId += assetId.get().getValue();

                                    assetTagId = tagId;
                                    assetTagStatus = false;

                                } else {
                                    assetTagId = tag;
                                    assetTagStatus = true;

                                }

                                Asset x = new Asset();

                                x.setType(type);
                                x.setModel(model);
                                x.setAssetTagId(assetTagId);
                                x.setAssetTagIdStatus(assetTagStatus);
                                x.setManufacturer(manufacturer);
                                x.setSupplier(supplier);
                                x.setData01(rowValues.get("data01"));
                                x.setData02(rowValues.get("data02"));
                                x.setData03(rowValues.get("data03"));
                                x.setData04(rowValues.get("data04"));
                                x.setData05(rowValues.get("data05"));
                                x.setData06(rowValues.get("data06"));
                                x.setData07(rowValues.get("data07"));
                                x.setData08(rowValues.get("data08"));
                                x.setData09(rowValues.get("data09"));
                                x.setData10(rowValues.get("data10"));
                                x.setData11(rowValues.get("data11"));
                                x.setData12(rowValues.get("data12"));
                                x.setData13(rowValues.get("data13"));
                                x.setData14(rowValues.get("data14"));
                                x.setData15(rowValues.get("data15"));
                                x.setData16(rowValues.get("data16"));

                                x.setSerialNo(serialNo);

                                Integer afterLoop = 4 + propertiesDetails.size();

                                try {
                                    x.setCost(Double.parseDouble(getStringCellValue(row.getCell(afterLoop + 1))));
                                } catch (Exception e) {
                                }

                                try {
                                    x.setPoNumber(getStringCellValue(row.getCell(afterLoop + 2)));
                                } catch (Exception e) {
                                }
                                try {
                                    x.setPoDate(convertToYYYYMMDD(getStringCellValue(row.getCell(afterLoop + 3))));
                                } catch (Exception e) {
                                }
                                try {
                                    x.setInvoiceNumber(getStringCellValue(row.getCell(afterLoop + 4)));
                                } catch (Exception e) {
                                }
                                try {
                                    x.setInvoiceDate(
                                            convertToYYYYMMDD(getStringCellValue(row.getCell(afterLoop + 5))));
                                } catch (Exception e) {

                                }
                                try {

                                    x.setWarrantyStatus(
                                        "yes".equalsIgnoreCase(getStringCellValue(row.getCell(afterLoop + 6))));
                                    
                                } catch (Exception e) {
                                    x.setWarrantyStatus(false);
                                }

                                if (x.isWarrantyStatus()) {
                                    x.setWarrantyStartDate(
                                            convertToYYYYMMDD(getStringCellValue(row.getCell(afterLoop + 7))));
                                    x.setWarrantyEndDate(
                                            convertToYYYYMMDD(getStringCellValue(row.getCell(afterLoop + 8))));
                                }

                                x.setCompanyId(user.getCompanyId());
                                x.setSuperCompanyId(user.getSuperCompanyId());
                                x.setStatus("Active");
                                x.setPreStatus("Active");
                                x.setActive(true);

                                x.setDismiss(false);
                                x.setFinanceDetails(false);
                                x.setAmcStatus(false);
                                x.setInsuranceStatus(false);
                                x.setSaleStatus(false);

                                assetRepository.save(x);

                                if (tag == null || tag.isEmpty() || tag.isBlank()) {
                                    Integer nextvalue = Integer.parseInt(assetId.get().getValue()) + 1;
                                    assetId.get().setValue(nextvalue.toString());
                                    applicationSetupRepository.save(assetId.get());
                                }

                            } else {
                                errorDate.add(serialNo);
                            }
                        });

                        if (errorDate.isEmpty()) {
                            return new ResponseEntity<>("Data uploaded successfully", HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(
                                    "Some Error(Duplicate Serial,Manufacturer Or Supplier Mismatch ) Found on Serial Numbers :"
                                            + String.join(", ", errorDate),
                                    HttpStatus.OK);
                        }

                    } else {
                        return new ResponseEntity<>("Total Asset Limit Exceeded, Available limit:"
                                + (subscription.get().getAsset() - assetCount), HttpStatus.OK);
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please Set Company Short Name");
                }

            } catch (IOException e) {
                return new ResponseEntity<>("Failed to upload data: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<?> uploadExcelForSupplier(FormData form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (form.getFile().isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        } else {
            try (InputStream inputStream = form.getFile().getInputStream()) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet.getRow(0).getLastCellNum() != 2) {
                    return new ResponseEntity<>("Invalid Excel format: Expected 2 columns", HttpStatus.BAD_REQUEST);
                }

                List<Supplier> sup = new ArrayList<>();
                List<String> errorDate = new ArrayList<>();

                sheet.iterator().forEachRemaining(row -> {
                    Supplier supplier = new Supplier();
                    supplier.setCompanyId(user.getCompanyId());
                    supplier.setSuperCompanyId(user.getSuperCompanyId());
                    supplier.setStatus(true);

                    if (row == null || row.getRowNum() == 0 || row.getCell(1) == null) {
                        return;
                    }

                    String type = getStringCellValue(row.getCell(0));
                    String name = getStringCellValue(row.getCell(1)).toUpperCase();

                    if (!supplierRepository.existsByNameAndTypeAndCompanyIdAndSuperCompanyId(name, type,
                            user.getCompanyId(), user.getSuperCompanyId())) {
                        supplier.setType(type);
                        supplier.setName(name);
                        sup.add(supplier);
                    } else {
                        errorDate.add(name);
                    }

                });

                sup.forEach(supplierRepository::save);

                if (errorDate.isEmpty()) {
                    return new ResponseEntity<>("Data uploaded successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Data uploaded But Some Error Found on Supplier :"
                            + String.join(", ", errorDate), HttpStatus.OK);
                }

            } catch (IOException e) {
                return new ResponseEntity<>("Failed to upload data: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public ResponseEntity<?> uploadExcelForAssetFinance(FormData form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (form.getFile().isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        } else {
            try (InputStream inputStream = form.getFile().getInputStream()) {
                Workbook workbook = WorkbookFactory.create(inputStream);
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet.getRow(0).getLastCellNum() != 7) {
                    return new ResponseEntity<>("Invalid Excel format: Expected 7 columns", HttpStatus.BAD_REQUEST);
                }

                List<Supplier> sup = new ArrayList<>();
                List<String> errorDate = new ArrayList<>();

                sheet.iterator().forEachRemaining(row -> {
                    if (row == null || row.getRowNum() == 0 || row.getCell(1) == null) {
                        return;
                    }

                    String tagId = getStringCellValue(row.getCell(0));
                    Long catId = Long.parseUnsignedLong(getStringCellValue(row.getCell(1)));
                    Long subCatId = Long.parseUnsignedLong(getStringCellValue(row.getCell(2)));
                    String depreciation = getStringCellValue(row.getCell(3));
                    String putToUseDate = getStringCellValue(row.getCell(4));
                    String method = getStringCellValue(row.getCell(5));
                    Integer life = Integer.parseInt(getStringCellValue(row.getCell(6)));

                    Optional<Asset> asset = assetRepository.findByAssetTagIdAndCompanyIdAndSuperCompanyId(tagId,
                            user.getCompanyId(), user.getSuperCompanyId());

                    Optional<Integer> assetLife = assetCategoryRepository.findLifeByCategoryAndSubCategory(
                            user.getCompanyId(), user.getSuperCompanyId(), catId, subCatId);

                    if (asset.isPresent() && assetLife.isPresent() && !asset.get().isFinanceDetails()) {

                        AssetDetails det = new AssetDetails();
                        det.setAssetCategoryId(catId);
                        det.setAssetSubCategoryId(subCatId);
                        det.setLife(assetLife.get());
                        det.setCompanyId(user.getCompanyId());
                        det.setSuperCompanyId(user.getSuperCompanyId());
                        det.setAsset(asset.get());

                        det.setDepreciableAsset("yes".equalsIgnoreCase(depreciation));
                        if (det.isDepreciableAsset()) {
                            det.setDepreciableMethod(method.toUpperCase());
                            det.setAssetLife(life);
                            det.setCost(asset.get().getCost());
                            det.setSalvageValue(det.getCost() * 5 / 100);
                            det.setPutToUseDateAvailability(true);
                            det.setPutToUseDate(putToUseDate);
                        }

                        asset.get().setFinanceDetails(true);
                        assetRepository.save(asset.get());
                        assetDetailsRepository.save(det);

                    } else {
                        errorDate.add(tagId);
                    }
                });
                if (errorDate.isEmpty()) {
                    return new ResponseEntity<>("Data uploaded successfully", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(
                            "Data uploaded But Some Error(Asset Not Found/Already Entered) Found on this AssetId :"
                                    + String.join(", ", errorDate),
                            HttpStatus.OK);
                }

            } catch (IOException e) {
                return new ResponseEntity<>("Failed to upload data: " + e.getMessage(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        CellType cellType = cell.getCellType();

        switch (cellType) {
            case STRING:
                String stringValue = cell.getStringCellValue();
                // Replace non-breaking spaces (U+00A0) with regular spaces (U+0020)
                stringValue = stringValue.replace('\u00A0', ' ');
                // Trim the string
                return stringValue.trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    return dateFormat.format(date);
                } else {
                    // Treat numeric values as strings
                    String numericValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                    // Replace non-breaking spaces (U+00A0) with regular spaces (U+0020)
                    numericValue = numericValue.replace('\u00A0', ' ');
                    // Trim the string
                    return numericValue.trim();
                }
            default:
                return null;
        }
    }

    private void extractImages(XSSFWorkbook workbook, Map<Integer, String> imagePaths, String type) throws IOException {
        List<XSSFPictureData> pictures = workbook.getAllPictures();
        if (!pictures.isEmpty()) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            File uploadDir = new File(UPLOAD_DIR + "/" + type);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            for (XSSFShape shape : sheet.getDrawingPatriarch().getShapes()) {
                UUID uuid = UUID.randomUUID();
                if (shape instanceof XSSFPicture) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFPictureData pictureData = picture.getPictureData();
                    XSSFClientAnchor anchor = picture.getPreferredSize();
                    int rowNum = anchor.getRow1();

                    String ext = pictureData.suggestFileExtension();
                    byte[] data = pictureData.getData();
                    String imagePath = UPLOAD_DIR + "/" + type + "/" + uuid.toString() + "." + ext;
                    String accessPath = "uploads/" + type + "/" + uuid.toString() + "." + ext;
                    try (FileOutputStream fos = new FileOutputStream(imagePath)) {
                        fos.write(data);
                    }
                    imagePaths.put(rowNum, accessPath);
                }
            }
        }
    }

    public static String generateFiscalYearCode(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date inputDate = sdf.parse(dateString);

        // Create a calendar instance and set the given date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);

        // Determine the fiscal year start date (e.g., April 1st of the current year)
        calendar.set(Calendar.MONTH, Calendar.APRIL);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Check if the given date is before or after the fiscal year start date
        if (inputDate.before(calendar.getTime())) {
            // If before, consider the previous year as the start of fiscal year
            calendar.add(Calendar.YEAR, -1);
        }

        // Get the fiscal year start and end years
        int fiscalYearStartYear = calendar.get(Calendar.YEAR);
        int fiscalYearEndYear = fiscalYearStartYear + 1;

        // Format the fiscal year code as "YYYY-YY"
        String fiscalYearCode = fiscalYearStartYear + "-" + String.format("%02d", fiscalYearEndYear % 100);

        return fiscalYearCode;
    }

    public static String convertToYYYYMMDD(String dateString) {
        String[] possibleFormats = { "MM/dd/yyyy", "M/dd/yyyy", "MM/d/yyyy", "M/d/yyyy",
                "dd/MM/yyyy", "d/M/yyyy", "yyyy/MM/dd", "yyyy/MM/d",
                "dd-MM-yyyy", "d-M-yyyy", "yyyy-MM-dd", "yyyy-M-dd",
                "dd.MM.yyyy", "d.M.yyyy", "yyyy.MM.dd", "yyyy.M.dd" };

        for (String format : possibleFormats) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.setLenient(false); // To avoid lenient date parsing
                Date date = dateFormat.parse(dateString);
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            } catch (ParseException ignored) {
                // If parsing fails, continue trying other formats
                continue;
            }
        }

        // If none of the formats succeed, return null or handle the error accordingly
        return null;
    }

}
