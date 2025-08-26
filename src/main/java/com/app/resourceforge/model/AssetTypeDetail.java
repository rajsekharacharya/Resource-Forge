package com.app.resourceforge.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Setter
@Getter
@ToString
@Entity
@Table(name = "assetTypeDetail")
@EntityListeners(AuditingEntityListener.class)
public class AssetTypeDetail extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;
    private String model;
    private String type;
    private String unit;
    private Integer maxLength;
    private Integer minValue;
    private Integer maxValue;
    private Boolean required;

    private Integer companyId;

    private Integer superCompanyId;

    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detail_id", nullable = false)
    @JsonIgnoreProperties("assetTypeDetail")
    private AssetType assetType;

    private Long assetTypeId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "assetTypeDetail")
    @JsonIgnoreProperties("assetTypeDetail")
    private Set<AssetTypeDetailDes> options;

    public AssetTypeDetail(String caption, String model, String type, boolean required, Long assetTypeId,
            AssetType assetType, Integer companyId, Integer superCompanyId) {
        this.caption = caption;
        this.model = model;
        this.type = type;
        this.required = required;
        this.companyId = companyId;
        this.superCompanyId = superCompanyId;
        this.status = true;
        this.assetTypeId = assetTypeId;
        this.assetType = assetType;
    }

    public void copyFromAssetDetails(AssetTypeDetail sourceAsset) {
        this.caption = sourceAsset.getCaption();
        this.model = sourceAsset.getModel();
        this.type = sourceAsset.getType();
        this.unit = sourceAsset.getUnit();
        this.maxLength = sourceAsset.getMaxLength();
        this.minValue = sourceAsset.getMinValue();
        this.maxValue = sourceAsset.getMaxValue();
        this.required = sourceAsset.getRequired();
        this.companyId = sourceAsset.getCompanyId();
        this.superCompanyId = sourceAsset.getSuperCompanyId();
        this.status = sourceAsset.isStatus();
        this.assetTypeId = sourceAsset.getAssetTypeId();

        // Copying relationships might require different handling based on your use case
        this.assetType = sourceAsset.getAssetType();
        this.options = sourceAsset.getOptions();
    }

}
