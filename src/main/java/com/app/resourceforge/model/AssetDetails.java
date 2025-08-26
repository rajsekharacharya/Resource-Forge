package com.app.resourceforge.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "assetDetails")
public class AssetDetails extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long assetCategoryId;

    private Long assetSubCategoryId;

    private Integer life;

    private Double cost;

    private boolean depreciableAsset;

    private String depreciableMethod;

    private Integer assetLife;

    private Double salvageValue;

    private boolean putToUseDateAvailability;

    private String putToUseDate;

    private Integer superCompanyId;

    private Integer companyId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    private Asset asset;

    @Transient
    private long tempAssetId;

}
