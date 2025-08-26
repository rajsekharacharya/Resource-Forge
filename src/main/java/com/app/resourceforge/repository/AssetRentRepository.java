package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.AssetRent;

public interface AssetRentRepository extends JpaRepository<AssetRent,Long> {
    
}
