package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.AssetInsurance;

@Repository
public interface AssetInsuranceRepository extends JpaRepository<AssetInsurance,Long> {
    
}
