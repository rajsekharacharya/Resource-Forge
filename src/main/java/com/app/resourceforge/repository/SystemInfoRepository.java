package com.app.resourceforge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.resourceforge.model.SystemInfo;

public interface SystemInfoRepository extends JpaRepository<SystemInfo, Long> {

        Optional<SystemInfo> findByUuid(String uuid);

        @Query("SELECT s FROM SystemInfo s WHERE s.companyCode = :companyCode AND (s.uuid = :uuid OR s.macAddress = :macAddress OR s.serialNumber = :serialNumber)")
        Optional<SystemInfo> findByCompanyCodeAndAnyIdentifier(
                        @Param("companyCode") String companyCode,
                        @Param("uuid") String uuid,
                        @Param("macAddress") String macAddress,
                        @Param("serialNumber") String serialNumber);

        Optional<SystemInfo> findByUuidAndCompanyCode(String uuid, String companyCode);

        Optional<SystemInfo> findByMacAddressAndCompanyCode(String macAddress, String companyCode);

        Optional<SystemInfo> findBySerialNumberAndCompanyCode(String serialNumber, String companyCode);

        Optional<SystemInfo> findByUuidOrSerialNumberAndCompanyCode(String uuid, String serialNumber,
                        String companyCode);

}
