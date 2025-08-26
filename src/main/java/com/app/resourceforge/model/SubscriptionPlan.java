package com.app.resourceforge.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table
@EntityListeners(AuditingEntityListener.class)
public class SubscriptionPlan extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer subsctptionMasterPlanId;

    private String planName;

    private Integer asset;

    private Integer company;

    private Integer superAdmin;

    private Integer user;

    private boolean mobileAccess;

    private boolean docUpload;

    private Double price;

    private Integer validity;

    private String note;

    private Integer superCompanyId;

    private boolean status;

    private boolean planRequest;

    private boolean isEditable;

    private LocalDate startDate;

    private LocalDate endDate;

    public SubscriptionPlan(Integer subsctptionMasterPlanId, String planName, Integer asset, Integer company,
            Integer superAdmin, Integer user, Double price,
            Integer validity, String note, Integer superCompanyId, boolean status, boolean planRequest,
            boolean isEditable, boolean mobileAccess, boolean docUpload) {
        this.subsctptionMasterPlanId = subsctptionMasterPlanId;
        this.planName = planName;
        this.asset = asset;
        this.company = company;
        this.user = user;
        this.superAdmin = superAdmin;
        this.price = price;
        this.validity = validity;
        this.note = note;
        this.superCompanyId = superCompanyId;
        this.status = status;
        this.planRequest = planRequest;
        this.isEditable = isEditable;
        this.mobileAccess = mobileAccess;
        this.docUpload = docUpload;
    }

}
