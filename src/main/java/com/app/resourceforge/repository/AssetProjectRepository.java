package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.AssetProject;

@Repository
public interface AssetProjectRepository extends JpaRepository<AssetProject, Long> {
    
}
