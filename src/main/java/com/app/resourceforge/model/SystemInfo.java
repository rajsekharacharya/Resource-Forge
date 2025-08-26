package com.app.resourceforge.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
public class SystemInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyCode;
    private String assetType;
    private String deviceName;
    private String uuid;
    private String macAddress;
    private String processor;
    private String os;
    private String ram;
    private String hddTotal;
    private String ssdTotal;
    private String availableRam;
    private String serialNumber;
    private String manufacturer;
    private String model;
    private String graphicsCard;
    private String graphicsCardVRam;
    private String antiVirus;
    private String ipAddress;
    @ElementCollection
    private List<InstalledSoftware> installedSoftware;
    @ElementCollection
    private List<Biosdata> bios;

}
