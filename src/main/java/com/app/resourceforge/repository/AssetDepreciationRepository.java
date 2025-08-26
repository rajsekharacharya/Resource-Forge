package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.AssetDepreciation;

public interface AssetDepreciationRepository extends JpaRepository<AssetDepreciation,Long> {
    
}
