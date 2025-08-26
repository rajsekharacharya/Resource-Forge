package com.app.resourceforge.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.ApplicationSetup;

public interface ApplicationSetupRepository extends JpaRepository<ApplicationSetup,Integer> {

    List<ApplicationSetup> findBySuperCompanyIdAndCompanyId(Integer superCompanyId,Integer companyId);

    List<ApplicationSetup> findByStatusAndSuperCompanyIdAndCompanyId(boolean status, Integer superCompanyId,Integer companyId);

    Optional<ApplicationSetup> findByKeyAndStatusAndSuperCompanyIdAndCompanyId(String key,boolean status, Integer superCompanyId,Integer companyId);
    
}
