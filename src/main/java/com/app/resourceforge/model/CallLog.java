package com.app.resourceforge.model;

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
public class CallLog extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long assetID;

    private Integer superCompanyId;

    private Integer companyId;

    private String timeStamp;

    private String note;

    private String status;

    private Integer dismissBy;
    private String dismissByName;

    private Integer assignTo;
    private String assignToName;
    private String assignDate;
    private String priority;

    private Integer resolvedBy;
    private String resolvedByName;
    private String resolvedDate;

    private String reason;
    private Integer reasonFrom;
    private String reasonFromName;

    private boolean active;

}
