package com.app.resourceforge.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.app.resourceforge.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "asset")
public class Asset extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetTagId;

    private boolean assetTagIdStatus;

    private String type;

    private String data01;
    private String data02;
    private String data03;
    private String data04;
    private String data05;
    private String data06;
    private String data07;
    private String data08;
    private String data09;
    private String data10;
    private String data11;
    private String data12;
    private String data13;
    private String data14;
    private String data15;
    private String data16;

    private Long systemApplication;

    private String deviceName;

    private String uuid;

    private String serialNo;

    private String model;

    private String manufacturer;

    private String supplier;

    private Double cost;

    private String invoiceDate;

    private String invoiceNumber;

    private String poDate;

    private String poNumber;

    private boolean warrantyStatus;

    private String warrantyStartDate;

    private String warrantyEndDate;

    private Integer companyId;

    private Integer superCompanyId;

    private String reserveDept;

    private String status;

    private String preStatus;

    private String location;

    private String subLocation;

    private String note;

    private boolean active = true;

    private boolean dismiss = false;

    private boolean financeDetails = false;

    private boolean amcStatus = false;

    private boolean insuranceStatus = false;

    private boolean saleStatus = false;

    private String saleType;

    private String buyer;

    private String saleDate;

    private String soldPerson;

    private String soldEmployeeId;

    private Double saleValue;

    private Double actualSaleValue;

    private boolean multiProductStatus;

    private Integer license;

    @Transient
    private Set<String> serialNos;

    @Transient
    private String assignPicture;

    @Transient
    private boolean inTransit = false;

    @Transient
    private boolean docUpload;
    @Transient
    private List<InstalledSoftware> installedSoftware;
    @Transient
    private List<Biosdata> bios;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private AssetDetails assetDetails;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetAssignHistory> assetAssignHistory;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetCustomerAssign> assetCustomerAssign;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetMaintenances> assetMaintenances;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetDepreciation> assetDepreciation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetDeployment> assetDeployment;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetAmc> assetAmc;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetInsurance> assetInsurance;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetProject> assetProject;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private Set<AssetRent> assetRent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asset")
    @JsonIgnoreProperties("asset")
    private List<AssetVersion> version;

    public void copyFromAsset(Asset sourceAsset) {
        this.id = sourceAsset.getId();
        this.assetTagId = sourceAsset.getAssetTagId();
        this.assetTagIdStatus = sourceAsset.isAssetTagIdStatus();
        this.type = sourceAsset.getType();
        this.data01 = sourceAsset.getData01();
        this.data02 = sourceAsset.getData02();
        this.data03 = sourceAsset.getData03();
        this.data04 = sourceAsset.getData04();
        this.data05 = sourceAsset.getData05();
        this.data06 = sourceAsset.getData06();
        this.data07 = sourceAsset.getData07();
        this.data08 = sourceAsset.getData08();
        this.data09 = sourceAsset.getData09();
        this.data10 = sourceAsset.getData10();
        this.data11 = sourceAsset.getData11();
        this.data12 = sourceAsset.getData12();
        this.data13 = sourceAsset.getData13();
        this.data14 = sourceAsset.getData14();
        this.data15 = sourceAsset.getData15();
        this.data16 = sourceAsset.getData16();
        this.systemApplication = sourceAsset.getSystemApplication();
        this.deviceName = sourceAsset.getDeviceName();
        this.uuid = sourceAsset.getUuid();
        this.model = sourceAsset.getModel();
        this.manufacturer = sourceAsset.getManufacturer();
        this.supplier = sourceAsset.getSupplier();
        this.serialNo = sourceAsset.getSerialNo();
        this.cost = sourceAsset.getCost();
        this.invoiceDate = sourceAsset.getInvoiceDate();
        this.invoiceNumber = sourceAsset.getInvoiceNumber();
        this.poDate = sourceAsset.getPoDate();
        this.poNumber = sourceAsset.getPoNumber();
        this.warrantyStatus = sourceAsset.isWarrantyStatus();
        this.warrantyStartDate = sourceAsset.getWarrantyStartDate();
        this.warrantyEndDate = sourceAsset.getWarrantyEndDate();
        this.superCompanyId = sourceAsset.getSuperCompanyId();
        this.companyId = sourceAsset.getCompanyId();
        this.reserveDept = sourceAsset.getReserveDept();
        this.status = sourceAsset.getStatus();
        this.preStatus = sourceAsset.getPreStatus();
        this.location = sourceAsset.getLocation();
        this.subLocation = sourceAsset.getSubLocation();
        this.note = sourceAsset.getNote();
        this.active = sourceAsset.isActive();
        this.dismiss = sourceAsset.isDismiss();
        this.financeDetails = sourceAsset.isFinanceDetails();
        this.amcStatus = sourceAsset.isAmcStatus();
        this.insuranceStatus = sourceAsset.isInsuranceStatus();
        this.saleStatus = sourceAsset.isSaleStatus();
        this.saleType = sourceAsset.getSaleType();
        this.buyer = sourceAsset.getBuyer();
        this.saleDate = sourceAsset.getSaleDate();
        this.soldPerson = sourceAsset.getSoldPerson();
        this.soldEmployeeId = sourceAsset.getSoldEmployeeId();
        this.saleValue = sourceAsset.getSaleValue();
        this.actualSaleValue = sourceAsset.getActualSaleValue();
        // Copying relationships might require different handling based on your use case
        this.assetDetails = sourceAsset.getAssetDetails();
        this.assetAssignHistory = sourceAsset.getAssetAssignHistory();
        this.assetMaintenances = sourceAsset.getAssetMaintenances();
        this.assetDepreciation = sourceAsset.getAssetDepreciation();
        this.assetDeployment = sourceAsset.getAssetDeployment();
        this.assetAmc = sourceAsset.getAssetAmc();
        this.assetInsurance = sourceAsset.getAssetInsurance();
        this.assetProject = sourceAsset.getAssetProject();
        this.assetRent = sourceAsset.getAssetRent();
        this.assetRent = sourceAsset.getAssetRent();
        this.multiProductStatus = sourceAsset.isMultiProductStatus();
        this.license = sourceAsset.getLicense();
    }

}
