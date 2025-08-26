package com.app.resourceforge.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AssetStatusDTO {

    public String type;
    public String insuranceName;
    public Long assetId;
    public Long callId;
    public Long  employeeId;
    public String  employeeCode;
    public String  employeeName;
    public String  department;
    public String  reserveDept;
    public Long  supplierId;
    public String supplierName;
    public String service;
    public Long  locationId;
    public String locationName;
    public Long  subLocationId;
    public String subLocationName;
    public String maintenanceType;
    public String startDate;
    public String endDate;
    public String note;
    public String link;
    public Double amount;
    
}
