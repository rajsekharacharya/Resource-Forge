package com.app.resourceforge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    List<Manufacturer> findByStatusAndCompanyId(boolean status, Integer companyId);

    List<Manufacturer> findByCompanyId(Integer companyId);

    boolean existsByNameAndCompanyId(String name, Integer companyId);

    Optional<Manufacturer> findByNameAndCompanyIdAndSuperCompanyId(String name, Integer companyId,
            Integer superCompanyId);

}
