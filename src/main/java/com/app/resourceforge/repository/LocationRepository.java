package com.app.resourceforge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.Location;

public interface LocationRepository extends JpaRepository<Location,Long> {

    List<Location> findByStatusAndCompanyId(boolean status, Integer companyId);
    List<Location> findByCompanyId(Integer companyId);
    boolean existsByNameAndCompanyIdAndSuperCompanyId(String name,Integer companyId,Integer superCompanyId);

    
}
