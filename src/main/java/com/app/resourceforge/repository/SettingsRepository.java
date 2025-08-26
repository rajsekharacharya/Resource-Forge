package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.resourceforge.model.Settings;
import java.util.List;


@Repository
public interface SettingsRepository extends JpaRepository<Settings,Integer> {

    List<Settings> findBySuperCompanyId(Integer superCompanyId);
    
}
